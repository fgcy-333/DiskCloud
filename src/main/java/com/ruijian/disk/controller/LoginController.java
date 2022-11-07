package com.ruijian.disk.controller;

import com.ruijian.disk.common.R;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@CrossOrigin
public class LoginController {


    /**
     * 登录
     *
     * @return
     */
    @GetMapping("/login")
    public R login() {
        return R.success()
                .setData("token", "admin")
                .setData("portalUserId", "100007")
                .setData("roles", "[admin]")
                .setData("name", "admin")
                .setData("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .setData("rootFolderId", "2");
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/info")
    public R info() {
        return R.success().setData("roles", "[admin]")
                .setData("name", "admin")
                .setData("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public R logout() {
        return R.success();
    }
}
