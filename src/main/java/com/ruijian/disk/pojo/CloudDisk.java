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
public class CloudDisk {
    private Long diskId;

    private Long portalUserId;

    private String userNick;

    private Date registerTime;

    private String imagePath;

    private Integer currentSize;

    private Integer maxSize;

    private Long folderId;

    private Integer openStatus;


}