package com.huiminpay.api;

import com.huiminpay.dto.MerchantDTO;

//商铺的业务层接口
public interface MerchantServiceApi {

    //根据id查询某个商户
    public MerchantDTO queryMerchantById(Long id);


    //商户注册
    public MerchantDTO registerMerchant(MerchantDTO merchantDto);

    //商户资质申请包含图片上传后的路径和其他基本信息
    MerchantDTO applyMerchant(Long merchantId, MerchantDTO merchantDto);
}
