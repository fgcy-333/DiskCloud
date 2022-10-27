package com.ruijian.disk.service.impl;

import com.ruijian.disk.mapper.CloudFileMapper;
import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.util.HdfsUtil;
import com.ruijian.disk.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (fileId != null && !StringUtil.inNumber(String.valueOf(fileId))) {
            throw new Exception(fileId + " is not a number");
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
        return cloudFileMapper.getFileObjByFolderId(folderId);
    }

    /**
     * 文件上传
     *
     * @param srcPath
     * @param dstPath
     * @param override
     * @return
     */
    @Override
    public boolean uploadFile(String srcPath, String dstPath, boolean override) throws Exception {
        return hdfsUtil.uploadFile(srcPath, dstPath);
    }

}
