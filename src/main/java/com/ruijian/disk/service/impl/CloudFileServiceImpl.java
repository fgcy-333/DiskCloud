package com.ruijian.disk.service.impl;

import com.ruijian.disk.mapper.CloudFileMapper;
import com.ruijian.disk.pojo.CloudFile;
import com.ruijian.disk.service.CloudFileService;
import com.ruijian.disk.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudFileServiceImpl implements CloudFileService {
    @Autowired
    private CloudFileMapper cloudFileMapper;

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


    @Override
    public List<CloudFile> getFileObjByFolderId(Long folderId) {
        cloudFileMapper.
    }
}
