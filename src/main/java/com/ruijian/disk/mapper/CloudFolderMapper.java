package com.ruijian.disk.mapper;

import com.ruijian.disk.pojo.CloudFolder;

public interface CloudFolderMapper {
    int deleteByPrimaryKey(Long folderId);

    int insert(CloudFolder record);

    int insertSelective(CloudFolder record);

    CloudFolder selectByPrimaryKey(Long folderId);

    int updateByPrimaryKeySelective(CloudFolder record);

    int updateByPrimaryKey(CloudFolder record);
}