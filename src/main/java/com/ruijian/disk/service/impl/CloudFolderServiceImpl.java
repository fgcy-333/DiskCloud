package com.ruijian.disk.service.impl;

import com.ruijian.disk.mapper.CloudFolderMapper;
import com.ruijian.disk.pojo.CloudFolder;
import com.ruijian.disk.service.CloudFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class CloudFolderServiceImpl implements CloudFolderService {
    @Autowired
    private CloudFolderMapper cloudFolderMapper;

    /**
     * 获取某个文件夹中下（一层）所有的文件夹
     *
     * @param folderId
     * @return
     */
    @Override
    public List<CloudFolder> getFolderObjsByParentFolderId(Long folderId) {
        return cloudFolderMapper.getFoldersByParentFolderId(folderId);
    }

    /**
     * 获取当前 hdfs中文件夹路径
     *
     * @param folderId
     * @return
     */
    @Override
    public String getCurrentPathByFolderId(Long folderId) {
        final CloudFolder cloudFolder = cloudFolderMapper.selectByPrimaryKey(folderId);
        final String folderPath = cloudFolder.getFolderPath();
        final String hdfsFolderName = cloudFolder.getHdfsFolderName();
        return folderPath + File.separator + hdfsFolderName;
    }
}
