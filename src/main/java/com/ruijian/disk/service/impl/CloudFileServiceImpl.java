package com.ruijian.disk.service.impl;

import com.ruijian.disk.mapper.CloudFileMapper;
import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.util.HdfsUtil;
import com.ruijian.disk.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CloudFileServiceImpl implements CloudFileService {
    @Autowired
    private CloudFileMapper cloudFileMapper;

    @Autowired
    private HdfsUtil hdfsUtil;

    /**
     * 获取文件 大小
     *
     * @param fileId
     * @return
     */
    @Override
    public int getSizeByFileId(Long fileId) throws Exception {
        if (fileId == null) {
            return 0;
        }
        return cloudFileMapper.getSizeByFileId(fileId);
    }


    /**
     * 根据文件夹id 获取 归属于该文件夹的文件
     *
     * @param folderId
     * @return
     */
    @Override
    public List<CloudFile> getFileObjsByFolderId(Long folderId) {
        if (folderId == null) {
            return null;
        }
        return cloudFileMapper.getFileObjByFolderId(folderId);
    }


    /**
     * 添加一条文件记录
     *
     * @param file
     */
    @Override
    public void addFileRecord(CloudFile file) {
        file.setUploadTime(new Timestamp(System.currentTimeMillis()));
        file.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        cloudFileMapper.insertSelective(file);
    }

    /**
     * 判断是否文件拥有者
     *
     * @param userId
     * @param fileId
     * @return
     */
    @Override
    public boolean checkFileOwner(Long userId, Long fileId) {
        if (userId == null || fileId == null) {
            return false;
        }
        final CloudFile file = cloudFileMapper.selectByPrimaryKey(fileId);
        return file.getPortalUserId().equals(userId);
    }


    @Override
    public CloudFile getFileObjByFileId(Long fileId) {
        if (fileId == null) {
            return null;
        }
        return cloudFileMapper.selectByPrimaryKey(fileId);
    }
}
