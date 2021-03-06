package com.yww.servicebase;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;

import java.util.ArrayList;

/**
 * @ClassName SwaggerConfig
 * @Descriprtion swagger的配置类
 * @Author yww
 * @Date 2021/1/28 17:37
 * @Version 1.0
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * swagger信息配置
     * @return Docket
     */
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ywwAPI")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    /**
     * 自定义apiInfo方法实现自定义信息配置
     * @return API用户信息
     */
    private ApiInfo webApiInfo(){
        //创建一个contact对象以便输入
        Contact contact = new Contact("yw", "localhost:8080/", "1141950370@qq.com");
        return new ApiInfo(
                //标题
                "Yw的swagger文档",
                //描述
                "永远相信美好的事情即将发生",
                //版本号
                "v1.0",
                //termsOfServiceUrl
                "urn:tos",
                //contact对象
                contact,
                //许可信息
                "Apacher 2.0",
                //许可信息的链接
                "http://www.apache.org/licenses/LINCENSE-2.0 ",
                new ArrayList());
    }
}
