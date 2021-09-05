package com.huiminpay.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SailingTest {

    @Test
    public void createSmsTest() {
        //下面参数的设置都是为了往sailing项目中的生成验证信息的方法中传参数
        //定义的请求路径中包含了所需的两个参数，具体数据封装进了map集合中

        //创建RestTemplate对象进行远程调用
        RestTemplate restTemplate = new RestTemplate();

        //定义请求路径
        String url="http://localhost:56085/sailing/generate?effectiveTime=300&name=sms";

        String phone="18239717831";
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile",phone);//此处的键名必须是mobile

        //指定请求的格式类型为json(设置请求头信息)
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        //将要发送的信息设置请求头中进行发送
        HttpEntity<HashMap<String, Object>> hashMapHttpEntity = new HttpEntity<>(map, httpHeaders);

        //通过RestTemplate进行远程调用  ResponseEntity<Map>短信接口返回的数据结果就封装在Map对象中
        ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, hashMapHttpEntity, Map.class);

        //获取返回的结果
        Map exchangeBody = exchange.getBody();
        Set set = exchangeBody.entrySet();

        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
