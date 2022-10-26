package com.ruijian.disk.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * HDFS 配置信息
 */
@Data
@Component
public class HdfsConfig {

    // hdfs nameNode连接URL
    @Value("${hdfs.url}")
    private String nameNodeUrl;

    // 操作用户
    @Value("${hdfs.userName}")
    private String hdfsUserName;

    // 操作存储节点路径
    @Value("${hdfs.dataNode}")
    private String pdfDataNode;

}

