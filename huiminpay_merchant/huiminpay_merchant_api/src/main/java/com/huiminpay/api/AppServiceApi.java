package com.huiminpay.api;

import com.huiminpay.common.cache.exception.BusinessException;
import com.huiminpay.dto.AppDTO;

import java.util.List;

/**
 * @author dupengfei
 * @date 2021/9/15 21:13
 * @Description:
 */
//商户下面的应用
public interface AppServiceApi {
    //添加应用
    public AppDTO insertApp(Long merchantId, AppDTO appDTO)throws BusinessException ;

    //根据自己的商户id查询本商户下的应用
    public List<AppDTO> queryAppsByMerchantId(Long merchantId) throws BusinessException;

    //根据应用id查询应用的详细信息
    public AppDTO queryAppByAppId(String appId) throws BusinessException;
}
