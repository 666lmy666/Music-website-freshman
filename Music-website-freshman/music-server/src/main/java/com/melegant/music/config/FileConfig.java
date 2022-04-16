package com.melegant.music.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//定位各种文件地址
@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        歌手头像地址
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                        +System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator")
        );
//        歌曲头像地址
        registry.addResourceHandler("/img/songPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                        +System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator")
        );
//        歌单头像地址
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                        +System.getProperty("file.separator")+"songListPic"+System.getProperty("file.separator")
        );
//        歌曲存放地址（file.separator是分隔符）
        registry.addResourceHandler("/song/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"song"+System.getProperty("file.separator")
        );
//        前端用户头像存放地址
        registry.addResourceHandler("/avatorImages/").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"avatorImages"+System.getProperty("file.separator")
        );
//        用户头像默认存放地址
        registry.addResourceHandler("/img/**").addResourceLocations(
                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+System.getProperty("file.separator")
        );
    }
}
