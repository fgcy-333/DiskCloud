package com.ruijian.disk.controller;

import com.ruijian.disk.common.Code;
import com.ruijian.disk.common.R;
import com.ruijian.disk.pojo.CloudDisk;
import com.ruijian.disk.pojo.vo.UserSearchObj;
import com.ruijian.disk.service.CloudDiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloudDisk")
public class CloudDiskController {
    @Autowired
    private CloudDiskService cloudDiskService;

    @GetMapping("getPage/{offset}/{limit}")
    public R getAllDisk(@PathVariable("limit") Integer limit,
                        @PathVariable("offset") Integer offset,
                        UserSearchObj userSearchObj) {

        List<CloudDisk> list = cloudDiskService.getAllDiskByConditions(limit, offset,userSearchObj);
        long total = cloudDiskService.getTotalCount();
        return R.success().setData("list", list).setData("total", total);
    }


    @DeleteMapping("/{id}")
    public R ban(@PathVariable("id") Long id) {
        if (cloudDiskService.disableState(id)) {
            return R.success();
        }
        return R.fail(Code.DISABLE_DISK_FAIL);
    }
}
