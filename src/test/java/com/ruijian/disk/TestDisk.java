package com.ruijian.disk;

import com.ruijian.disk.mapper.CloudDiskMapper;
import com.ruijian.disk.common.Const;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDisk {
    @Autowired
    private CloudDiskMapper cloudDiskMapper;

    @Test
    public void test1() {
        cloudDiskMapper.updateUserSize(1000, Const.ADD_DISK_SPACE, 2L);
    }
}
