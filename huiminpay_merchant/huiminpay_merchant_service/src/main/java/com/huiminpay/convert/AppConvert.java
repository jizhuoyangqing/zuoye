package com.huiminpay.convert;

import com.huiminpay.dto.AppDTO;
import com.huiminpay.dto.MerchantDTO;
import com.huiminpay.entity.App;
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
public interface AppConvert {

    //创建转换构建器
    AppConvert Instance = Mappers.getMapper(AppConvert.class);
    //entity-->dto
    public AppDTO App2dto(App app);

    //dto-->entity
    public App dto2App(AppDTO appDTO);

    public List<AppDTO> listApp2ListAppDto(List<App> apps);
}
