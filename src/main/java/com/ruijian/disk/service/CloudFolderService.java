package com.ruijian.disk.service;

import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.pojo.CloudFolder;

import java.util.List;

public interface CloudFolderService {


    List<CloudFolder> getFolderObjsByParentFolderId(Long folderId);

    String getCurrentPathByFolderId(Long folderId);
}
