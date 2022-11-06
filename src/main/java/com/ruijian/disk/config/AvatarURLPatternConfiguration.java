package com.ruijian.disk.config;

import com.ruijian.disk.common.Const;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class AvatarURLPatternConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/avatar/**") //虚拟url路径
                .addResourceLocations("file:" + Const.AVATAR_PATH ); //真实本地路径
    }
}
