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
public class CloudFileShare {
    private Long shareId;

    private Long portalUserId;

    private Long fileId;

    private String fileName;

    private Integer fileType;

    private String shareUrl;

    private Integer status;

    private Integer validityType;

    private Date validityTime;

    private Date createTime;

    private String password;

    private String shareCode;
}