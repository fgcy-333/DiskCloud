package com.ruijian.disk.service;

import com.ruijian.disk.pojo.CloudDisk;
import com.ruijian.disk.pojo.vo.UserSearchObj;

import java.util.List;

public interface CloudDiskService {

    CloudDisk getCloudDiskInfoByDiskId(Long diskId);

    boolean updateDiskUsageFile(Long fileId, Long diskId, String opt);

    boolean updateDiskUsageFolder(Long folderId, Long diskId, String opt) throws Exception;

    CloudDisk getDiskInfoByUserId(Long userId);

    List<CloudDisk> getAllDiskByConditions(Integer limit, Integer offset, UserSearchObj userSearchObj);

    long getTotalCount();

    boolean disableState(Long id);
}
