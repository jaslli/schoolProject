package com.yww.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName UcenterApplication
 * @Descriprtion ucenter服务启动类
 * @Author yww
 * @Date 2021/2/18 20:12
 * @Version 1.0
 **/

@SpringBootApplication
@ComponentScan({"com.yww"})
@MapperScan("com.yww.educenter.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
