package com.yww.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName MsmAppliacation
 * @Descriprtion MSM模块的启动类
 * @Author yww
 * @Date 2021/2/18 17:27
 * @Version 1.0
 **/

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.yww")
public class MsmAppliacation {
    public static void main(String[] args) {
        SpringApplication.run(MsmAppliacation.class, args);
    }
}
