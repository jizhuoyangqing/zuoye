package com.huiminpay.serevice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huiminpay.api.MerchantServiceApi;
import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.exception.BusinessExceptionUtil;
import com.huiminpay.common.cache.util.PhoneUtil;
import com.huiminpay.convert.MerchantConvert;
import com.huiminpay.dto.MerchantDto;
import com.huiminpay.entity.Merchant;
import com.huiminpay.mapper.MerchantMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MerchantServiceImpl implements MerchantServiceApi {
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
        BeanUtils.copyProperties(merchant, merchantDto);
        return merchantDto;
    }

    //商户注册
    @Override
    @Transactional //该方法被事务管理
    public MerchantDto registerMerchant(MerchantDto merchantDto) {

        //为了代码的健壮性，都要先处理异常
        //校验必要参数（防止脚本直接访问此方法进行攻击）
        if (merchantDto == null) {
            //用自己的工具类抛异常
            BusinessExceptionUtil.cast(CommonErrorCode.E_100101);
        }
        //校验关键参数
        if (StringUtils.isEmpty(merchantDto.getMobile())) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_100112);
        }
        //不为空就校验手机号的格式，使用工具类进行校验(用的正则表达式)
        if (!PhoneUtil.isMatches(merchantDto.getMobile())) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_100109);
        }
        if (StringUtils.isEmpty(merchantDto.getUsername())) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_100110);
        }


        //做手机号唯一性校验
        Integer integer = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getMobile, merchantDto.getMobile()));
        if (integer > 0) { //说明已经注册过了
            BusinessExceptionUtil.cast(CommonErrorCode.E_100113);
        }


        //Merchant merchant = new Merchant();
        /*
        这样的对象参数的传递太原始，可以用工具类
        merchant.setMobile(merchantDto.getMobile());*/

        //使用springframework包下的BeanUtils，把字段相同的merchantDto中的字段的值传递给merchant中的字段的值
        //BeanUtils.copyProperties(merchantDto,merchant);

        //使用mapstruct完成对象转换
        Merchant merchant = MerchantConvert.Instance.dto2Merchant(merchantDto);


        //可以先设置审核状态  0-未申请,1-已申请待审核,2-审核通过,3-审核拒绝
        merchant.setAuditStatus("0");

        //执行插入数据到数据库的操作
        merchantMapper.insert(merchant);

        //根据插入后回显的id，设置到merchantDto中的id属性中
        //淘汰的方式merchantDto.setId(merchant.getId());
        //BeanUtils.copyProperties(merchant,merchantDto);
        return MerchantConvert.Instance.Merchant2dto(merchant);
    }

    @Transactional //该方法被事务管理
    //商户资质申请包含图片上传后的路径和其他基本信息
    @Override
    public MerchantDto applyMerchant(Long merchantId, MerchantDto merchantDto) {
        //为了代码的健壮性，校验必要参数（防止脚本直接访问此方法进行攻击）
        if (merchantDto == null || merchantId==null) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_110006);
        }


        //转换实体类
        Merchant merchant = MerchantConvert.Instance.dto2Merchant(merchantDto);
        //可以先设置审核状态  0-未申请,1-已申请待审核,2-审核通过,3-审核拒绝
        merchant.setAuditStatus("1");
        merchant.setId(merchantId);
        merchantMapper.updateById(merchant);
        return MerchantConvert.Instance.Merchant2dto(merchant);
    }
}
