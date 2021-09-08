package com.huiminpay.service;

import com.huiminpay.api.MerchantServiceApi;
import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.exception.BusinessExceptionUtil;
import com.huiminpay.common.cache.util.PhoneUtil;
import com.huiminpay.convert.MerchantRegisterConvert;
import com.huiminpay.dto.MerchantDto;
import com.huiminpay.vo.MerchantVo;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {
    @Reference
    MerchantServiceApi merchantServiceApi;

    @Autowired
    SmsService smsService;


    //商户注册
    public MerchantVo registerMerchant(MerchantVo merchantVo) {

        //为了代码的健壮性，都要先处理异常
        if (merchantVo==null){
            //用自己的工具类抛异常
            BusinessExceptionUtil.cast(CommonErrorCode.E_100101);
        }
        //校验关键参数
        if (StringUtils.isEmpty(merchantVo.getMobile())){
            BusinessExceptionUtil.cast(CommonErrorCode.E_100112);
        }
        if (StringUtils.isEmpty(merchantVo.getUsername())){
            BusinessExceptionUtil.cast(CommonErrorCode.E_100110);
        }
        if (StringUtils.isEmpty(merchantVo.getPassword())){
            BusinessExceptionUtil.cast(CommonErrorCode.E_100111);
        }
        //不为空就校验手机号的格式，使用工具类进行校验(用的正则表达式)
        if (!PhoneUtil.isMatches(merchantVo.getMobile())){
            BusinessExceptionUtil.cast(CommonErrorCode.E_100109);
        }






        //校验验证码 这里抛异常后就不会往在下面走，不会完成注册
        smsService.verify(merchantVo.getVerifiykey(),merchantVo.getVerifiyCode());


        //商户的注册
        //MerchantDto merchantDto = new MerchantDto();

        //第一种用法 merchantDto.setMobile(merchantVo.getMobile());
        //第二种用法 BeanUtils.copyProperties(merchantVo,merchantDto);
        //第三种用法 原先还得new Merchant 现在不用new了，可以直接返回该对象
        MerchantDto merchantDto = MerchantRegisterConvert.INSTANCE.vo2dto(merchantVo);

        merchantServiceApi.insertMerchant(merchantDto);
        return merchantVo;
    }
}
