package com.ruijian.disk.controller;

import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.service.CloudFolderService;
import com.ruijian.disk.util.Code;
import com.ruijian.disk.util.HdfsUtil;
import com.ruijian.disk.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/cloudFile")
public class CloudFileController {
    @Value("${file-save-path}")
    private String fileSavePath;
    private static Logger log = LoggerFactory.getLogger(CloudFileController.class);

    @Autowired
    private HdfsUtil hdfsUtil;

    @Autowired
    private CloudFolderService cloudFolderService;

    @PostMapping("uploadFile")
    public R uploadFile(MultipartFile uploadFile,
                        @RequestParam(value = "userId") Long userId,
                        @RequestParam(value = "folderId") Long folderId) {
        try {

            final long size = uploadFile.getSize();
            String name = uploadFile.getOriginalFilename();
            String postfix = name.substring(name.indexOf("."));

            File folder = new File(fileSavePath);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            //上传至hdfs
            // TODO: 2022/10/27 以后可以弄一个大文件秒传
            String folderPath = cloudFolderService.getCurrentPathByFolderId(folderId);
            String tempFilePath = folder + name;
            uploadFile.transferTo(new File(tempFilePath));
            final boolean upload = hdfsUtil.uploadFile(tempFilePath, folderPath);
            if (upload) {

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail(Code.UPLOAD_FAIL);
        }
        return R.success(null);
    }


}
