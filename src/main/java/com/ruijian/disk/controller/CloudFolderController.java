package com.ruijian.disk.controller;

import com.ruijian.disk.pojo.CloudFolder;
import com.ruijian.disk.service.CloudFolderService;
import com.ruijian.disk.util.Code;
import com.ruijian.disk.util.HdfsUtil;
import com.ruijian.disk.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class CloudFolderController {
    @Autowired
    private CloudFolderService folderService;
    @Autowired
    private HdfsUtil hdfsUtil;

    @Value("${file.save.path}")
    private String fileSavePath;

    Logger log = LoggerFactory.getLogger(CloudFolderController.class);

    public ResponseEntity<ByteArrayResource> downloadFolder(Long userId, Long folderId) {
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
            ZipOutputStream zipOutputStream = ZipUtil.putFileToZipEntry(listFile, paramIds,cloudFolder.getFileFolderName());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            final HashMap<String, String> returnMap = new HashMap<>();
            returnMap.put("code", String.valueOf(Code.DOWNLOAD_ERROR.getCode()));
            returnMap.put("msg", Code.DOWNLOAD_ERROR.getMsg());
            return new ResponseEntity(returnMap, HttpStatus.BAD_REQUEST);
        }


    }
}
