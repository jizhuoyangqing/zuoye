package com.huiminpay.serevice;

import com.huiminpay.api.MerchantService;
import com.huiminpay.api.dto.MerchantDto;
import com.huiminpay.bean.Merchant;
import com.huiminpay.mapper.MerchantMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Service
public class  MerchantServiceImpl implements MerchantService {
    //根据id查询某个商户
    @Autowired
    MerchantMapper merchantMapper;
    @Override
    public Merchant queryMerchantById(Long id) {
        return merchantMapper.selectById(id);
    }

    //商户注册
    @Override
    public MerchantDto insertMerchant(MerchantDto merchantDto) {
        Merchant merchant = new Merchant();
        merchant.setMobile(merchantDto.getMobile());
        merchant.setAuditStatus("0");
        //执行插入数据到数据库的操作
        merchantMapper.insert(merchant);

        //根据插入后回显的id，设置到merchantDto中的id属性中
        merchantDto.setId(merchant.getId());
        return merchantDto;
    }
}
