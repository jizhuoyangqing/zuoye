package com.huiminpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class})*/
public class MerchantApplication {
    public static void main(String[] args) {
        SpringApplication.run(MerchantApplication.class,args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
