package com.huiminpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //开启Swagger的配置
public class Swagger2Config {
    //固定写法：只需要改变apiInfo和包路径即可
    //指定Swagger生效的基础目录(Swagger注解要配置到哪些目录下)
    @Bean
    public Docket docket() {
        //指定swagger文档的类型
        return new Docket(DocumentationType.SWAGGER_2)
                //指定生成的swagger文档的基础信息。这里根据情况改变
                .apiInfo(getApiInfo()) //传入一个ApiInfo的对象，用下面的方法传进来
                //做查询
                .select()
                //指定扫描的基础包路径。这里根据情况改变
                .apis(RequestHandlerSelectors.basePackage("com.huiminpay.controller"))//代表的是application中的包
                //
                .paths(PathSelectors.any())
                //这里是构建对象
                .build();
    }

    //构建API基础信息(在网页中显示的信息)
    public ApiInfo getApiInfo(){
        //这三个参数分别指定：文档谁写的，个人网页的地址，个人邮箱
        //不构建此对象的话，ApiInfo中默认三个参数都为空
        Contact cont = new Contact("DPF","","3064717379");
        return new ApiInfoBuilder()
                //网页中显示的标题
                .title("惠民支付API接口文档")
                //对标题的描述
                .description("该文档由后端编写，供前端进行测试使用")
                //如果自定义了联系人名称就写
                .contact(cont)
                //指定该API的版本
                .version("V1.0.0")
                //构建 ApiInfo对象
                .build();

    }
}
