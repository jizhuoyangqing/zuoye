package com.huiminpay.api;

//验证码接口
public interface SmsService {
    //获取验证码的方法
    public String sendSms(String phone);
    //验证码校验的方法
    //验证码的key值由前端传递(一点击验证码，就会返回一个key值存在前端)
    //验证码的值由用户传递(一点击获取验证码，就会返回一个验证码给用户手机,用户自己输入即可)
    public String verify(String verifyKey,String verifyCode);
}
