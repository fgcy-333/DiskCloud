package com.ruijian.disk.mapper;

import com.ruijian.disk.pojo.CloudFile;

public interface CloudFileMapper {
    int deleteByPrimaryKey(Long fileId);

    int insert(CloudFile record);

    int insertSelective(CloudFile record);

    CloudFile selectByPrimaryKey(Long fileId);

    int updateByPrimaryKeySelective(CloudFile record);

    int updateByPrimaryKey(CloudFile record);
}