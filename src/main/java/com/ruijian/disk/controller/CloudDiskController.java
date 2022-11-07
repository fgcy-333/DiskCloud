package com.ruijian.disk.controller;

import com.ruijian.disk.common.Code;
import com.ruijian.disk.common.Const;
import com.ruijian.disk.common.R;
import com.ruijian.disk.pojo.CloudDisk;
import com.ruijian.disk.pojo.vo.UserSearchObj;
import com.ruijian.disk.service.CloudDiskService;
import com.ruijian.disk.util.QiniuUtils;
import com.ruijian.disk.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cloudDisk")
public class CloudDiskController {
    private final Logger log = LoggerFactory.getLogger(CloudDiskController.class);
    @Autowired
    private CloudDiskService cloudDiskService;


    @GetMapping("getPage/{offset}/{limit}")
    public R getAllDisk(@PathVariable("limit") Integer limit,
                        @PathVariable("offset") Integer offset,
                        UserSearchObj userSearchObj) {

        List<CloudDisk> list = cloudDiskService.getAllDiskByConditions(limit, offset, userSearchObj);
        long total = cloudDiskService.getTotalCount();
        return R.success().setData("list", list).setData("total", total);

    }

    /**
     * 禁用用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R banUser(@PathVariable("id") Long id) {
        if (cloudDiskService.disableState(id)) {
            return R.success();
        }
        return R.fail(Code.DISABLE_DISK_FAIL);
    }


    /**
     * 上传头像
     *
     * @param avatarFile
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public R addUser(@RequestPart("file") MultipartFile avatarFile, HttpServletRequest request) {
        try {
            final String originalFilename = avatarFile.getOriginalFilename();
            final String suffix = originalFilename.substring(originalFilename.indexOf("."));
            String hdfsFileName = StringUtil.getUniqueStr(10) + suffix;
            final StringBuilder sb = new StringBuilder();
            sb.append(request.getScheme()).append(":").append("//").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath()).append("/").append(Const.AVATAR).append("/").append(hdfsFileName);
            avatarFile.transferTo(new File(Const.AVATAR_PATH + hdfsFileName));
            return R.success().setData("url", sb.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.fail(Code.UPLOAD_FAIL);
        }
    }

    @PutMapping("/modify")
    public R modifyDisk() {
        return R.success();
    }

}
