package com.ruijian.disk.service.impl;

import com.ruijian.disk.mapper.CloudDiskMapper;
import com.ruijian.disk.mapper.CloudFileMapper;
import com.ruijian.disk.pojo.CloudDisk;
import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.pojo.CloudFolder;
import com.ruijian.disk.service.CloudDiskService;
import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.service.CloudFolderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger log = LoggerFactory.getLogger(CloudDiskServiceImpl.class);

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
        return updateUserSize(size, opt, diskId);
    }

    /**
     * 更新个人磁盘占用
     *
     * @param size
     * @param opt
     * @param diskId
     * @return
     */
    private boolean updateUserSize(int size, String opt, Long diskId) {
        return updateUserSize(size, opt, diskId);
    }


    /**
     * 根据文件夹id 增加个人磁盘占用
     *
     * @param folderId
     * @return
     */
    @Override
    public boolean updateDiskUsageFolder(Long folderId, Long diskId, String opt) throws Exception {
        int size = getSizeByFolderId(folderId);
        return updateUserSize(size, opt, diskId);
    }

    /**
     * 获取某个文件夹大小
     *
     * @param folderId
     * @return
     * @throws Exception
     */
    private int getSizeByFolderId(Long folderId) throws Exception {
        final ArrayList<Integer> param = new ArrayList<>();
        param.add(0);
        getSizeRecursion(folderId, param);
        return param.get(0);
    }

    /**
     * 递归获取 某个文件夹的大小
     *
     * @param folderId
     * @param param
     * @throws Exception
     */
    private void getSizeRecursion(Long folderId, ArrayList<Integer> param) throws Exception {
        Integer size = param.get(0);
        //处理文件夹中的文件
        List<CloudFile> cloudFiles = cloudFileService.getFileObjsByFolderId(folderId);
        for (CloudFile cloudFile : cloudFiles) {
            final int sizeByFileId = cloudFileService.getSizeByFileId(cloudFile.getFileId());
            size += sizeByFileId;
        }
        //处理文件夹中的文件夹
        List<CloudFolder> folders = cloudFolderService.getFolderObjsByParentFolderId(folderId);
        for (CloudFolder folder : folders) {
            getSizeRecursion(folder.getFolderId(), param);
        }
    }

}
