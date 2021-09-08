package com.huiminpay.controller;

import com.huiminpay.api.MerchantServiceApi;
import com.huiminpay.dto.MerchantDto;
import com.huiminpay.service.MerchantService;
import com.huiminpay.service.SmsService;
import com.huiminpay.vo.MerchantVo;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "商品应用API接口", description = "商品应用API接口,包含增删改查功能")
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Reference
    MerchantServiceApi merchantServiceApi;

    @Autowired
    SmsService smsService;

    @Autowired
    private MerchantService merchantService;


    //@ApiOperation(value = "根据商铺的id获取商铺信息",httpMethod ="GET")
    //用GETMapping后不用写httpMethod ="GET"
    @ApiOperation(value = "根据商铺的id获取商铺信息")

    @ApiImplicitParams(                //required = true代表是一个必填项
            {
                    @ApiImplicitParam(name = "merchantId", value = "商铺id", required = true, dataType = "long"),
                    //如果有多个参数就这样加
                    @ApiImplicitParam(name = "merchantName", value = "商铺名称", required = false, dataType = "string")
            }
    )
    //需要@ApiImplicitParam的name属性与Restful风格的访问值一样，这样才能在页面中只显示一个同样的参数框
    @GetMapping("/queryMerchantById/{merchantId}/{merchantName}")
    public MerchantDto queryMerchantById(@PathVariable("merchantId") Long id,
                                         @PathVariable("merchantName") String name) {
        System.out.println(name);
        return merchantServiceApi.queryMerchantById(id);
    }


    @ApiOperation(value = "根据传递的merchant信息返回结果")
    @PostMapping("/queryByMerchant")
    public void query(MerchantDto merchantDto) {
        System.out.println(merchantDto.toString());
    }


    //用户输入手机号，返回给前端一个验证码的key
    //在校验验证码的地方，用户输入发送给他的验证码,暂时在sailing的控制台打印了(在校验验证码的地方做)
    @ApiOperation("用户输入手机号，返回给前端一个验证码的key")
    @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, dataType = "string")
    @GetMapping("/sendPhone/{phone}")
    public String sengSms(@PathVariable("phone") String phoneNumber) {
        String smsKey = smsService.sendSms(phoneNumber);
        return smsKey;
    }


    //商户注册接口（同时校验验证码）
    @PostMapping("/registerMerchant")
    @ApiOperation(value = "先校验，在注册的方法")
    public MerchantVo registerMerchant(MerchantVo merchantVo) {
        //只用controller做调用,业务抽取到了MerchantService中
        return merchantService.registerMerchant(merchantVo);

    }
}
