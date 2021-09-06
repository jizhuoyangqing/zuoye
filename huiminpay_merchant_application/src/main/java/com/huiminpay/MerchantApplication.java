package com.huiminpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class})*/
public class MerchantApplication {
    public static void main(String[] args) {
        SpringApplication.run(MerchantApplication.class,args);
    }
}
