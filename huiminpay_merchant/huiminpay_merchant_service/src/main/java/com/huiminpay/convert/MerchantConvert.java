package com.huiminpay.convert;

import com.huiminpay.dto.MerchantDTO;
import com.huiminpay.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dupengfei
 * @date 2021/9/7 8:39
 * @Description:
 */
@Mapper
public interface MerchantConvert {

    //创建转换构建器
    MerchantConvert Instance = Mappers.getMapper(MerchantConvert.class);
    //entity-->dto
    public MerchantDTO Merchant2dto(Merchant merchant);

    //dto-->entity
    public Merchant dto2Merchant(MerchantDTO merchantDto);

    public List<MerchantDTO> listMerchant2ListMerchantDto(List<Merchant> merchants);
}
