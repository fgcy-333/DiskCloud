package com.ruijian.disk.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserSearchObj {
    private String name;
    private Integer openStatus;
    private Date begin;
    private Date end;
}
