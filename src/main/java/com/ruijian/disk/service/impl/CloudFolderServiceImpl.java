package com.ruijian.disk.service.impl;

import com.ruijian.disk.common.Const;
import com.ruijian.disk.mapper.CloudFolderMapper;
import com.ruijian.disk.pojo.CloudFolder;
import com.ruijian.disk.service.CloudFolderService;
import com.ruijian.disk.util.HdfsUtil;
import com.ruijian.disk.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CloudFolderServiceImpl implements CloudFolderService {
    @Autowired
    private CloudFolderMapper cloudFolderMapper;

    @Autowired
    private HdfsUtil hdfsUtil;

    private Logger log = LoggerFactory.getLogger(CloudFolderServiceImpl.class);

    /**
     * 获取某个文件夹中下（一层）所有的文件夹
     *
     * @param folderId
     * @return
     */
    @Override
    public List<CloudFolder> getFolderObjsByParentFolderId(Long folderId) {
        if (folderId == null) {
            return null;
        }
        return cloudFolderMapper.getFoldersByParentFolderId(folderId);
    }

    /**
     * 获取当前 hdfs中文件夹路径
     *
     * @param folderId
     * @return
     */
    @Override
    public String getCurrentPathByFolderId(Long folderId) {
        if (folderId == null) {
            return null;
        }
        final CloudFolder cloudFolder = cloudFolderMapper.selectByPrimaryKey(folderId);
        final String folderPath = cloudFolder.getFolderPath();
        final String hdfsFolderName = cloudFolder.getHdfsFolderName();
        if (cloudFolder.getParentFolderId().equals(Const.ROOT_PARENT_ID)) {
            return File.separator + hdfsFolderName;
        }
        return File.separator + folderPath + File.separator + hdfsFolderName;
    }

    /**
     * 判断该文件夹是否属于用户
     *
     * @param userId
     * @param folderId
     * @return
     */
    @Override
    public boolean checkFolderOwner(Long userId, Long folderId) {
        if (userId == null || folderId == null) {
            return false;
        }
        final CloudFolder folder = cloudFolderMapper.selectByPrimaryKey(folderId);
        return folder.getPortalUserId().equals(userId);
    }

    /**
     * 根据文件夹id 获取文件夹对象
     *
     * @param folderId
     * @return
     */
    @Override
    public CloudFolder getFolderObjByFolderId(Long folderId) {
        if (folderId == null) {
            return null;
        }
        return cloudFolderMapper.selectByPrimaryKey(folderId);
    }

    @Override
    public HashMap<String, Object> listFolder(Long id) {
        final CloudFolder currentFolder = cloudFolderMapper.selectByPrimaryKey(id);
        final HashMap<String, Object> currentMap = folderFormat(currentFolder, Const.ROOT_PARENT_ID);
        final List<CloudFolder> childrenFolders = cloudFolderMapper.getFoldersByParentFolderId(id);
        if (childrenFolders != null || !childrenFolders.isEmpty()) {
            final ArrayList<HashMap<String, Object>> arr = new ArrayList<>();
            for (CloudFolder childrenFolder : childrenFolders) {
                arr.add(folderFormat(childrenFolder, currentFolder.getFolderId()));
            }
            currentMap.put("childrenList", arr);
        } else {
            currentMap.put("childrenList", null);
        }
        return currentMap;
    }

    private HashMap<String, Object> folderFormat(CloudFolder currentFolder, long parentId) {
        final HashMap<String, Object> currentMap = new HashMap<>();
        if (currentFolder == null) {
            log.error("转换文件夹格式时发现文件夹对象为空");
            return currentMap;
        }
        currentMap.put("id", currentFolder.getFolderId());
        currentMap.put("name", currentFolder.getFileFolderName() + "/");
        currentMap.put("parentId", parentId);
        currentMap.put("childrenList", null);
        return currentMap;
    }


    @Override
    public void renameFile(CloudFolder cloudFolder) throws Exception {
        if (cloudFolder == null) {
            throw new Exception("cloudFolder为空");
        }
        Long folderId = cloudFolder.getFolderId();
        String fileFolderName = cloudFolder.getFileFolderName();

        if (folderId == null || StringUtil.isBlank(fileFolderName)) {
            throw new Exception("folderId或fileFolderName为空");
        }
        cloudFolder = null;
        cloudFolder.setFolderId(folderId);
        cloudFolder.setFileFolderName(fileFolderName);
        cloudFolderMapper.updateByPrimaryKeySelective(cloudFolder);
    }


    @Override
    public void newFolder(CloudFolder cloudFolder) throws Exception {
        Long parentFolderId = cloudFolder.getParentFolderId();
        String fileFolderName = cloudFolder.getFileFolderName();
        if (parentFolderId == null || StringUtil.isBlank(fileFolderName)) {
            throw new Exception("parentFolderId或fileFolderName为空");
        }

        //获取父文件夹信息
        CloudFolder parentFolder = cloudFolderMapper.selectByPrimaryKey(parentFolderId);
        //父级文件夹
        String folderPath = parentFolder.getFolderPath();
        //当前文件夹
        String hdfsFolderName = parentFolder.getHdfsFolderName();
        //新建文件夹
        String uniqueStr = StringUtil.getUniqueStr(8);

        //新文件夹的路径
        String pathForNew = null;
        if (parentFolder.getParentFolderId().equals(Const.ROOT_PARENT_ID)) {
            pathForNew = hdfsFolderName;
        } else {
            pathForNew = folderPath + File.separator + hdfsFolderName;
        }

        //在hadoop上新建一个文件夹
        hdfsUtil.mkdir(pathForNew + File.separator + uniqueStr);


        //添加数据库记录
        CloudFolder folder = new CloudFolder();
        folder.setFileFolderName(fileFolderName);
        folder.setParentFolderId(parentFolderId);
        folder.setHdfsFolderName(uniqueStr);
        folder.setPortalUserId(cloudFolder.getPortalUserId());
        folder.setCreateTime(new Date());
        folder.setUpdateTime(new Date());
        folder.setIsDelete(Const.FORMAL);
        folder.setFolderPath(pathForNew);
        folder.setFolderType(Const.PERSONAL_FOLDER);
        folder.setGroupId(Const.NO_GROUP_ID);
        cloudFolderMapper.insertSelective(folder);
    }
}
