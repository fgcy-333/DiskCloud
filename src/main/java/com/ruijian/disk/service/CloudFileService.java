package com.ruijian.disk.service;

import com.ruijian.disk.pojo.CloudFile;

import java.util.List;

public interface CloudFileService {
    int getSizeByFileId(Long fileId) throws Exception;

    List<CloudFile> getFileObjsByFolderId(Long folderId);

    boolean uploadFile(String srcPath, String dstPath, boolean override) throws Exception;
}
