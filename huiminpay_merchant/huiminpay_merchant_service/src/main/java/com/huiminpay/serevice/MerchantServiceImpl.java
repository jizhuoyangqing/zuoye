package com.huiminpay.serevice;

import com.huiminpay.api.MerchantServiceApi;
import com.huiminpay.dto.MerchantDto;
import com.huiminpay.entity.Merchant;
import com.huiminpay.mapper.MerchantMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class  MerchantServiceImpl implements MerchantServiceApi {
    @Autowired
    MerchantMapper merchantMapper;

    //根据id查询某个商户
    @Override
    public MerchantDto queryMerchantById(Long id) {
        //查询出来Merchant对象
        Merchant merchant = merchantMapper.selectById(id);
        //new MerchantDto对象，便于把参数拷给MerchantDto
        MerchantDto merchantDto = new MerchantDto();
        //对象对拷,把merchant对象中的字段的值拷给merchantDto对象(字段的名字一致的才能拷)
        BeanUtils.copyProperties(merchant,merchantDto);
        return merchantDto;
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
