package com.ruijian.disk.controller;

import com.ruijian.disk.common.R;
import com.ruijian.disk.pojo.CloudFolder;
import com.ruijian.disk.service.CloudFolderService;
import com.ruijian.disk.common.Code;
import com.ruijian.disk.util.HdfsUtil;
import com.ruijian.disk.util.StringUtil;
import com.ruijian.disk.util.ZipUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * @param
     * @return
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFolder(Long userId,
                                                 String folderIds,
                                                 String fileIds) {
        try {
            HashMap<String, List<Long>> downloadTypeMap = new HashMap<>();

            if (StringUtil.isNotBlank(folderIds)) {
                List<Long> list = StringUtil.putStrToList(folderIds);
                downloadTypeMap.put("folder", list);
            }

            if (StringUtil.isNotBlank(fileIds)) {
                final List<Long> list = StringUtil.putStrToList(fileIds);
                downloadTypeMap.put("file", list);
            }

            final Map<String, String> map = ZipUtil.putFileToZipEntry(downloadTypeMap, fileSavePath);

            final ResponseEntity<byte[]> body = ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=".concat(map.get("name")))
                    .body(Files.readAllBytes(Paths.get(map.get("localPath"))));

            //删除zip
            new File(map.get("localPath")).delete();


            return body;
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
