package com.ruijian.disk.service;

import com.ruijian.disk.pojo.CloudDisk;

public interface CloudDiskService {

    CloudDisk getCloudDiskInfoByDiskId(Long diskId);

    boolean updateDiskUsageFile(Long fileId, Long diskId, String opt);

    boolean updateDiskUsageFolder(Long folderId, Long diskId, String opt) throws Exception;
}
