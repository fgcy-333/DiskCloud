package com.ruijian.disk.mapper;

import com.ruijian.disk.pojo.CloudFileShare;

public interface CloudFileShareMapper {
    int deleteByPrimaryKey(Long shareId);

    int insert(CloudFileShare record);

    int insertSelective(CloudFileShare record);

    CloudFileShare selectByPrimaryKey(Long shareId);

    int updateByPrimaryKeySelective(CloudFileShare record);

    int updateByPrimaryKey(CloudFileShare record);
}