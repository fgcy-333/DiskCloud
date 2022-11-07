package com.ruijian.disk.controller;

import com.ruijian.disk.common.R;
import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.pojo.CloudFolder;
import com.ruijian.disk.service.CloudFolderService;
import com.ruijian.disk.common.Code;
import com.ruijian.disk.util.HdfsUtil;
import com.ruijian.disk.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/folder")
public class CloudFolderController {
    @Autowired
    private CloudFolderService folderService;
    @Autowired
    private HdfsUtil hdfsUtil;

    @Value("${file.save.path}")
    private String fileSavePath;

    Logger log = LoggerFactory.getLogger(CloudFolderController.class);


    /**
     * 文件夹下载
     *
     * @param userId
     * @param folderId
     * @return
     */
    public ResponseEntity<ZipOutputStream> downloadFolder(Long userId, Long folderId) {
        try {
            //权限
            if (!folderService.checkFolderOwner(userId, folderId)) {
                final HashMap<String, String> returnMap = new HashMap<>();
                returnMap.put("code", String.valueOf(Code.NO_PERMISSION.getCode()));
                returnMap.put("msg", Code.NO_PERMISSION.getMsg());
                return new ResponseEntity(returnMap, HttpStatus.BAD_REQUEST);
            }

            CloudFolder cloudFolder = folderService.getFolderObjByFolderId(folderId);
            final String currentPath = folderService.getCurrentPathByFolderId(folderId);
            hdfsUtil.downloadFile(currentPath, fileSavePath + cloudFolder.getHdfsFolderName());
            List<File> files = new ArrayList<>();
            final File file = new File(fileSavePath + cloudFolder.getHdfsFolderName());
            files.add(file);
            final ArrayList<Long> paramIds = new ArrayList<>();
            paramIds.add(folderId);
            final List<Map<String, String>> listFile = hdfsUtil.listFile(cloudFolder.getFolderPath());
            ZipOutputStream zipOutputStream = ZipUtil.putFileToZipEntry(listFile, paramIds, cloudFolder.getFileFolderName());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-disposition", "attachment; filename=".concat(cloudFolder.getFileFolderName()))
                    .body(zipOutputStream);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            final HashMap<String, String> returnMap = new HashMap<>();
            returnMap.put("code", String.valueOf(Code.DOWNLOAD_ERROR.getCode()));
            returnMap.put("msg", Code.DOWNLOAD_ERROR.getMsg());
            return new ResponseEntity(returnMap, HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping("/list/{id}")
    @ResponseBody
    public R folderList(@PathVariable("id") Long id) {
        return R.success().setData("dir", folderService.listFolder(id));
    }


    @PostMapping("/rename")
    @ResponseBody
    public R renameFile(@RequestBody CloudFolder cloudFolder) {
        try {
            folderService.renameFile(cloudFolder);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail(Code.RENAME_ERR);
        }
        return R.success();
    }


    @ResponseBody
    @PostMapping("/newfolder")
    public R newFolder(@RequestBody CloudFolder cloudFolder) {
        try {
            folderService.newFolder(cloudFolder);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail(Code.NEW_FOLDER_FAIL);
        }
        return R.success();
    }
}
