package com.ruijian.disk.controller;

import com.ruijian.disk.common.Code;
import com.ruijian.disk.common.Const;
import com.ruijian.disk.common.R;
import com.ruijian.disk.pojo.CloudDisk;
import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.service.CloudDiskService;
import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.service.CloudFolderService;
import com.ruijian.disk.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

@Controller
@RequestMapping("/cloudFile")
public class CloudFileController {

    @Value("${file.save.path}")
    private String fileSavePath;

    private static final Logger log = LoggerFactory.getLogger(CloudFileController.class);

    @Autowired
    private HdfsUtil hdfsUtil;

    @Autowired
    private CloudFolderService cloudFolderService;

    @Autowired
    private CloudFileService cloudFileService;

    @Autowired
    private CloudDiskService cloudDiskService;

    @PostMapping("uploadFile")
    public R uploadFile(MultipartFile uploadFile,
                        @RequestParam(value = "userId") Long userId,
                        @RequestParam(value = "folderId") Long folderId) {
        try {
            final long size = uploadFile.getSize();
            String name = uploadFile.getOriginalFilename();
            String postfix = name.substring(name.indexOf("."));
            int type = Const.getFileType(postfix);

            File folder = new File(fileSavePath);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            //上传至hdfs
            // TODO: 2022/10/27 以后可以弄一个大文件秒传
            String folderPath = cloudFolderService.getCurrentPathByFolderId(folderId);
            String hdfsFileName = StringUtil.getUniqueStr();
            String tempFilePath = folder + hdfsFileName + postfix;
            uploadFile.transferTo(new File(tempFilePath));
            final boolean upload = hdfsUtil.uploadFile(tempFilePath, folderPath);

            //添加数据库记录
            CloudFile file = null;
            if (upload) {
                file = new CloudFile();
                file.setFileName(name);
                file.setPostfix(postfix);
                file.setType(type);
                file.setSize(Math.toIntExact(size));
                file.setFilePath(folderPath);
                file.setPortalUserId(userId);
                file.setHdfsFileName(hdfsFileName + postfix);
                cloudFileService.addFileRecord(file);
            }

            CloudDisk cloudDisk = cloudDiskService.getDiskInfoByUserId(userId);
            //增加磁盘占用
            cloudDiskService.updateDiskUsageFile(file.getFileId(), cloudDisk.getDiskId(), Const.ADD_DISK_SPACE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail(Code.UPLOAD_FAIL);
        }
        return R.success();
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(Long userId, Long fileId) {
        try {
            if (!cloudFileService.checkFileOwner(userId, fileId)) {
                final HashMap<String, String> returnMap = new HashMap<>();
                returnMap.put("code", String.valueOf(Code.NO_PERMISSION.getCode()));
                returnMap.put("msg", Code.NO_PERMISSION.getMsg());
                return new ResponseEntity(returnMap, HttpStatus.BAD_REQUEST);
            }

            CloudFile cloudFile = cloudFileService.getFileObjByFileId(fileId);
            if (cloudFile == null) {
                throw new Exception("没有该文件");
            }
            String srcFilePath = cloudFile.getFilePath() + cloudFile.getHdfsFileName();
            hdfsUtil.downloadFile(srcFilePath, fileSavePath);
            final File file = new File(fileSavePath + cloudFile.getHdfsFileName());
            if (!file.exists()) {
                throw new Exception("文件不存在,路径:" + fileSavePath + cloudFile.getHdfsFileName());
            }
            byte[] bytes = Files.readAllBytes(file.toPath());
            ByteArrayResource bar = new ByteArrayResource(bytes);
            file.delete();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-disposition", "attachment; filename=".concat(cloudFile.getFileName()))
                    .body(bar);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            final HashMap<String, String> returnMap = new HashMap<>();
            returnMap.put("code", String.valueOf(Code.DOWNLOAD_ERROR.getCode()));
            returnMap.put("msg", Code.DOWNLOAD_ERROR.getMsg());
            return new ResponseEntity(returnMap, HttpStatus.BAD_REQUEST);
        }

    }
}
