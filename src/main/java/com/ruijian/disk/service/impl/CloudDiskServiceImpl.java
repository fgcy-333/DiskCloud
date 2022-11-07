package com.ruijian.disk.service.impl;

import com.ruijian.disk.common.Const;
import com.ruijian.disk.mapper.CloudDiskMapper;
import com.ruijian.disk.mapper.CloudFileMapper;
import com.ruijian.disk.pojo.CloudDisk;
import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.pojo.CloudFolder;
import com.ruijian.disk.pojo.vo.UserSearchObj;
import com.ruijian.disk.service.CloudDiskService;
import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.service.CloudFolderService;

import com.ruijian.disk.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${page.limit}")
    private Integer size;
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
        //KB
        return updateUserSize(size / 1000, opt, diskId);
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
        return cloudDiskMapper.updateUserSize(size, opt, diskId);
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
        if (folders == null || folders.isEmpty()) {
            return;
        }

        for (CloudFolder folder : folders) {
            getSizeRecursion(folder.getFolderId(), param);
        }
    }


    /**
     * 根据用户id获取个人磁盘信息
     *
     * @param userId
     * @return
     */
    @Override
    public CloudDisk getDiskInfoByUserId(Long userId) {
        return cloudDiskMapper.getByUserId(userId);
    }


    /**
     * 分页获取磁盘信息
     *
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public List<CloudDisk> getAllDiskByConditions(Integer limit, Integer offset, UserSearchObj userSearchObj) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = size;
        }
        final List<CloudDisk> disks = cloudDiskMapper.pageSelect(offset, limit, userSearchObj);
        disks.forEach(obj -> {
            obj.setMaxSize(obj.getMaxSize() / 1024 / 1024);
            obj.setCurrentSize(obj.getCurrentSize() / 1024 / 1024);
        });
        return disks;
    }

    /**
     * 获取总记录数
     *
     * @return
     */
    @Override
    public long getTotalCount() {
        return cloudDiskMapper.getTotalCount();
    }


    /**
     * 禁用磁盘
     *
     * @param id
     * @return
     */
    @Override
    public boolean disableState(Long id) {
        if (id == null) {
            return false;
        }
        final CloudDisk cloudDisk = new CloudDisk();
        cloudDisk.setDiskId(id);
        cloudDisk.setOpenStatus(Const.DISK_DISABLE);
        cloudDiskMapper.updateByPrimaryKeySelective(cloudDisk);
        return true;
    }
}
