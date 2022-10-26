package com.ruijian.disk.controller;

import com.ruijian.disk.mapper.CloudDiskMapper;
import com.ruijian.disk.pojo.CloudDisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloudDisk")
public class CloudDiskController {
    @Autowired
    private CloudDiskMapper cloudDiskMapper;

    @RequestMapping("/hello")
    public String hello() {
        final CloudDisk cloudDisk = cloudDiskMapper.selectByPrimaryKey(2L);
        return cloudDisk.toString();
    }
}
