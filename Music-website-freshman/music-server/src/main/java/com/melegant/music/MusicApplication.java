package com.melegant.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
热加载热更新
1、ctrl+shift+A,搜索registry，找到compiler.automake.allow.when.app.running打上勾
2、启动：Ctrl+F9
 */

/*启动类启动时需要扫描dao的mapper接口*/
@MapperScan("com.melegant.music.dao")
@SpringBootApplication
public class MusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class, args);
    }

}
