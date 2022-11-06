package com.ruijian.disk.service;

import com.ruijian.disk.pojo.CloudFile;

import java.util.List;

public interface CloudFileService {
    int getSizeByFileId(Long fileId) throws Exception;

    List<CloudFile> getFileObjsByFolderId(Long folderId);


    void addFileRecord(CloudFile file);


    boolean checkFileOwner(Long userId, Long fileId);

    CloudFile getFileObjByFileId(Long fileId);

    void renameFile(CloudFile cloudFile) throws Exception;
}
