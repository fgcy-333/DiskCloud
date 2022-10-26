package com.ruijian.disk.mapper;

import com.ruijian.disk.pojo.CloudDisk;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CloudDiskMapper {
    int deleteByPrimaryKey(Long diskId);

    int insert(CloudDisk record);

    int insertSelective(CloudDisk record);

    CloudDisk selectByPrimaryKey(Long diskId);

    int updateByPrimaryKeySelective(CloudDisk record);

    int updateByPrimaryKey(CloudDisk record);
}