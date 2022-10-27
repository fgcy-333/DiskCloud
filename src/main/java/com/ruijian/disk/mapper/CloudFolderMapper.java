package com.ruijian.disk.mapper;

import com.ruijian.disk.pojo.CloudFolder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CloudFolderMapper {
    int deleteByPrimaryKey(Long folderId);

    int insert(CloudFolder record);

    int insertSelective(CloudFolder record);

    CloudFolder selectByPrimaryKey(Long folderId);

    int updateByPrimaryKeySelective(CloudFolder record);

    int updateByPrimaryKey(CloudFolder record);

    List<CloudFolder> getFoldersByParentFolderId(Long folderId);
}