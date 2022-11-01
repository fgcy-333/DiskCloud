package com.ruijian.disk.controller;

import com.ruijian.disk.common.R;

import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class LoginController {
    @GetMapping("/login")
    public R login() {
        return R.success().setData("token", "admin");
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
