package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//创建启动类
@SpringBootApplication
@MapperScan("com.blog.mapper")
public class YSBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(YSBlogApplication.class,args);
    }
}
