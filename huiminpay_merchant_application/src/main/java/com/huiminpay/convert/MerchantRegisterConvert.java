package com.huiminpay.convert;

import com.huiminpay.dto.MerchantDto;
import com.huiminpay.vo.MerchantVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author dupengfei
 * @date 2021/9/6 23:54
 * @Description:
 */

@org.mapstruct.Mapper
public interface MerchantRegisterConvert {

    //创建转换构建器
    MerchantRegisterConvert INSTANCE = Mappers.getMapper(MerchantRegisterConvert.class);

    //vo-->dto
    public MerchantDto vo2dto(MerchantVo merchantVo);

    //dto-->vo
    public MerchantVo dto2vo(MerchantDto merchantDto);

}
