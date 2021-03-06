package com.yww.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName CmsApplication
 * @Descriprtion CMS服务的启动类
 * @Author yww
 * @Date 2021/2/10 15:53
 * @Version 1.0
 **/
@SpringBootApplication
@ComponentScan({"com.yww"})
@MapperScan("com.yww.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
