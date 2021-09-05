package com.huiminpay.api;

import com.huiminpay.api.dto.MerchantDto;
import com.huiminpay.bean.Merchant;

//商铺的业务层接口
public interface MerchantService {
    //根据id查询某个商户
    public Merchant queryMerchantById(Long id);

    //商户注册
    public MerchantDto insertMerchant(MerchantDto merchantDto);

}
