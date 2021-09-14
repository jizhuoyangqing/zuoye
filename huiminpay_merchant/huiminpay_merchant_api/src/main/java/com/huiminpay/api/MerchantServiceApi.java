package com.huiminpay.api;

import com.huiminpay.dto.MerchantDto;

//商铺的业务层接口
public interface MerchantServiceApi {

    //根据id查询某个商户
    public MerchantDto queryMerchantById(Long id);


    //商户注册
    public MerchantDto registerMerchant(MerchantDto merchantDto);

    //商户资质申请包含图片上传后的路径和其他基本信息
    MerchantDto applyMerchant(Long merchantId, MerchantDto merchantDto);
}
