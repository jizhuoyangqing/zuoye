package com.huiminpay.serevice;

import com.huiminpay.api.SmsServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@org.apache.dubbo.config.annotation.Service
public class SmsServiceImpl implements SmsServiceApi {

    @Value("${sms.url}")
    String url;

    @Value("${sms.effectiveTime}")
    String effectiveTime;

    @Value("${sms.name}")
    String name;

    @Autowired
    RestTemplate restTemplate;

    //获取验证码的接口
    @Override
    public String sendSms(String phone) {
        //下面参数的设置都是为了往sailing项目中的生成验证信息的方法中传参数
        //定义的请求路径中包含了所需的两个参数，具体数据封装进了map集合中


        //定义请求路径
        //String url = "http://localhost:56085/sailing/generate?effectiveTime=300&name=sms";
        String smsUrl = url + "/generate?effectiveTime=" + effectiveTime + "&name=" + name;


        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", phone);//此处的键名必须是mobile

        //指定请求的格式类型为json(设置请求头信息)
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        //将要发送的信息设置请求头中进行发送
        HttpEntity<HashMap<String, Object>> hashMapHttpEntity = new HttpEntity<>(map, httpHeaders);

        //通过RestTemplate进行远程调用  ResponseEntity<Map>短信接口返回的数据结果就封装在Map对象中
        ResponseEntity<Map> exchange = restTemplate.exchange(smsUrl, HttpMethod.POST, hashMapHttpEntity, Map.class);

        //获取返回的结果（只需要其中的key值）
        if (exchange != null) {
            Map exchangeBody = exchange.getBody();
            Object object = exchangeBody.get("result");
            if (object == null) { //代表验证码获取失败
                throw new RuntimeException("验证码获取失败");
            }
            Map<String, String> result = (Map<String, String>) object;
            String key = result.get("key");
            return key;
        }
        return null;
    }


    //验证码校验的接口
    //如果没有抛异常就代表验证成功
    @Override
    public String verify(String verifyKey, String verifyCode) {
        //http://localhost:56085/sailing/verify?name=sms&verificationCode=631311&verificationKey=sms%3Aa58cd08f0a834b35b23967658f2d6f4e
        String veriUrl = url + "/verify?name=sms&verificationCode=" + verifyCode + "&verificationKey=" + verifyKey;
        Map responseMap = null;
        try {
            //请求校验验证码
            ResponseEntity<Map> exchange = restTemplate.exchange(veriUrl, HttpMethod.POST,
                    HttpEntity.EMPTY, Map.class);
            responseMap = exchange.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException("验证码错误");
        }
        if (responseMap == null || responseMap.get("result") == null ||
                !(Boolean)responseMap.get("result")) {
            throw new RuntimeException("验证码错误");
        }
        return null;
    }
}
