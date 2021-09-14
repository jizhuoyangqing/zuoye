package com.huiminpay.controller;

import com.huiminpay.api.MerchantServiceApi;
import com.huiminpay.common.cache.util.SecurityUtil;
import com.huiminpay.dto.MerchantDto;
import com.huiminpay.service.MerchantService;
import com.huiminpay.service.SmsService;
import com.huiminpay.vo.MerchantVo;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "商品应用API接口", description = "商品应用API接口,包含增删改查功能")
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    //用Reference的是远程调用服务的
    //用Autowired的是application中的service业务处理
    @Reference
    private MerchantServiceApi merchantServiceApi;
    @Autowired
    private SmsService smsService;
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


    //这不是具体的业务，只是看看swagger的效果
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


    //商户注册接口（同时校验验证码，调用application层的service）
    @PostMapping("/registerMerchant")
    @ApiOperation(value = "先校验，在注册的方法")
    public MerchantVo registerMerchant(MerchantVo merchantVo) {
        //只用controller做调用,业务抽取到了MerchantService中
        return merchantService.registerMerchant(merchantVo);

    }


    //商户资质申请中的图片文件上传
    @ApiOperation("商户资质的图片上传")
    @ApiParam(name = "multipartFile", value = "上传的文件信息", required = true, type = "MultipartFile")
    @PostMapping("/uploadImg")
    public String upLoadImg(MultipartFile multipartFile) {
       return merchantService.uploadImg(multipartFile);
    }

    //商户资质申请包含图片上传后的路径和其他基本信息
    @ApiOperation("商户信息(包括资质)上传")
    @ApiImplicitParams(                //required = true代表是一个必填项
            {
                    @ApiImplicitParam(name = "merchantDto", value = "商户资质申请的信息",required = true, dataType = "MerchantDto"),
            }
    )
    @PostMapping("/my/merchants/save")
    public MerchantDto applyMerchant(@RequestBody MerchantDto merchantDto) {

        //有一个统一认证授权的系统，登陆成功后会生成一个令牌，从令牌中获取商户的id等信息
        //可以先用一个类测试，工作中再调令牌的接口就可以了

        //通过登陆生成的令牌中获取（先用手动写的令牌类生成令牌，再自己写的SecurityUtil工具类进行解析）
        //用SecurityUtil工具类进行获取其中的merchantId
        Long merchantId = SecurityUtil.getMerchantId();

        return merchantServiceApi.applyMerchant(merchantId,merchantDto);

    }
}
