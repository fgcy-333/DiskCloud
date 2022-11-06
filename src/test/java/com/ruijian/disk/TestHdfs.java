package com.ruijian.disk;

import com.ruijian.disk.util.HdfsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

//@SpringBootTest
public class TestHdfs {

    @Autowired
    private HdfsUtil hdfsUtil;

    @Test
    public void test1() {
        try {
            final List<Map<String, String>> list = hdfsUtil.listFile("/user/");
            for (Map<String, String> map : list) {
                final Set<String> keySet = map.keySet();
                for (String key : keySet) {
                    System.out.println(map.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void dsf() {
        System.getProperty("user.dir");
    }

}
