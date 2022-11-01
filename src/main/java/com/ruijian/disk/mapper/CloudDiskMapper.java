package com.ruijian.disk.mapper;

import com.ruijian.disk.pojo.CloudDisk;
import com.ruijian.disk.pojo.vo.UserSearchObj;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CloudDiskMapper {
    int deleteByPrimaryKey(Long diskId);

    int insert(CloudDisk record);

    int insertSelective(CloudDisk record);

    CloudDisk selectByPrimaryKey(Long diskId);

    int updateByPrimaryKeySelective(CloudDisk record);

    int updateByPrimaryKey(CloudDisk record);

    boolean updateUserSize(@Param("size") int size,
                           @Param("opt") String opt,
                           @Param("diskId") Long diskId
    );

    CloudDisk getByUserId(Long userId);

    List<CloudDisk> pageSelect(@Param("offset") Integer offset,
                               @Param("limit") Integer limit,
                               @Param("userSearchObj") UserSearchObj userSearchObj
    );

    long getTotalCount();
}