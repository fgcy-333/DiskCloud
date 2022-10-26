package com.ruijian.disk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CloudFolder {
    private Long folderId;

    private String fileFolderName;

    private Long parentFolderId;

    private String ftpFolderName;

    private Long portalUserId;

    private Date createTime;

    private Date updateTime;

    private Long groupId;

    private Integer folderType;

    private Integer isDelete;

}