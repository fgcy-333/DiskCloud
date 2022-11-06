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
public class CloudFile {
    private Long fileId;

    private String fileName;

    private String hdfsFileName;

    private Long portalUserId;

    private String filePath;

    private Integer downloadTimes;

    private Date uploadTime;

    private Long folderId;

    private Integer size;

    private String type;

    private Integer version;

    private String postfix;

    private Date updateTime;

    private Integer isDelete;
}