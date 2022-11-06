package com.ruijian.disk.service;

import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.pojo.CloudFolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CloudFolderService {


    List<CloudFolder> getFolderObjsByParentFolderId(Long folderId);

    String getCurrentPathByFolderId(Long folderId);

    boolean checkFolderOwner(Long userId, Long folderId);

    CloudFolder getFolderObjByFolderId(Long folderId);

    HashMap<String, Object> listFolder(Long id);
}
