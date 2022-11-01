package com.ruijian.disk.util;

import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.pojo.CloudFolder;
import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.service.CloudFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Component
public class ZipUtil {
    @Autowired
    private CloudFolderService folderService;

    @Autowired
    private CloudFileService cloudFileService;

    @Autowired
    private static ZipUtil zipUtil;

    @PostConstruct
    public void init() {
        zipUtil = this;
        zipUtil.folderService = this.folderService;
        zipUtil.cloudFileService = this.cloudFileService;
    }

    public static ZipOutputStream putFileToZipEntry(List<Map<String, String>> files, ArrayList<Long> paramIds, String folderName) {
        if ((files == null || files.isEmpty()) && (paramIds == null || paramIds.isEmpty())) {
            return null;
        }

        //单文件夹下载
        if (files.size() == 1) {
            final Long folderId = paramIds.get(0);
            final HashMap<String, String> fileNameMap = new HashMap<>();
            getAllNameByFolderId(folderId, fileNameMap);

            for (Map<String, String> file : files) {
                final String filePath = file.get("filePath");
                String realPath = zipUtil.hdfsPathToRealPath(filePath, fileNameMap, folderName);
            }
        }
        return null;
    }

    /**
     * 将远程路径 转为数据库中 记录了的文件真实路径
     *
     * @param filePath
     * @param fileNameMap
     * @param folderName
     * @return
     */
    // TODO: 2022/10/31
    private String hdfsPathToRealPath(String filePath, HashMap<String, String> fileNameMap, String folderName) {

        return null;
    }


    public static void getAllNameByFolderId(Long folderId, Map<String, String> fileNameMap) {
        //文件
        final List<CloudFile> fileObjs = zipUtil.cloudFileService.getFileObjsByFolderId(folderId);
        for (CloudFile fileObj : fileObjs) {
            fileNameMap.put(fileObj.getHdfsFileName(), fileObj.getFileName());
        }
        //文件夹
        final List<CloudFolder> folderObjs = zipUtil.folderService.getFolderObjsByParentFolderId(folderId);
        for (CloudFolder folderObj : folderObjs) {
            fileNameMap.put(folderObj.getHdfsFolderName(), folderObj.getFileFolderName());
            getAllNameByFolderId(folderObj.getFolderId(), fileNameMap);
        }

    }
}
