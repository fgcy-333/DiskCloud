package com.ruijian.disk.service.impl;

import com.ruijian.disk.mapper.CloudDiskMapper;
import com.ruijian.disk.mapper.CloudFileMapper;
import com.ruijian.disk.pojo.CloudDisk;
import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.service.CloudDiskService;
import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.service.CloudFolderService;
import com.ruijian.disk.util.Const;
import org.apache.ibatis.annotations.Param;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CloudDiskServiceImpl implements CloudDiskService {
    @Autowired
    private CloudDiskMapper cloudDiskMapper;

    @Autowired
    private CloudFileMapper cloudFileMapper;

    @Autowired
    private CloudFileService cloudFileService;

    @Autowired
    private CloudFolderService cloudFolderService;

    /**
     * 获取个人磁盘信息
     *
     * @param diskId
     * @return
     */
    @Override
    public CloudDisk getCloudDiskInfoByDiskId(Long diskId) {
        return cloudDiskMapper.selectByPrimaryKey(diskId);
    }

    /**
     * 根据文件id 修改个人磁盘占用
     *
     * @param fileId
     * @return
     */
    @Override
    public boolean updateDiskUsageFile(Long fileId, Long diskId, String opt) {
        final int size = cloudFileMapper.getSizeByFileId(fileId);
        return cloudDiskMapper.updateUserSize(size, opt, diskId);
    }


    /**
     * 根据文件夹id 增加个人磁盘占用
     *
     * @param folderId
     * @return
     */
    @Override
    public boolean updateDiskUsageFolder(Long folderId, Long diskId, String opt) {
        int size = getSizeByFolderId(folderId);
        // TODO: 2022/10/26
        return true;
    }

    private int getSizeByFolderId(Long folderId) {
        final ArrayList<Integer> param = new ArrayList<>();
        param.add(0);
        getSizeRecursion(folderId, param);
        return param.get(0);
    }

    private void getSizeRecursion(Long folderId, ArrayList<Integer> param) {
        final Integer size = param.get(0);
        List<CloudFile> cloudFiles = cloudFileService.getFileObjByFolderId(folderId);
    }

}
