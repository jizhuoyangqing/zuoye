package com.huiminpay.serevice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huiminpay.api.AppServiceApi;
import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.exception.BusinessException;
import com.huiminpay.common.cache.exception.BusinessExceptionUtil;
import com.huiminpay.convert.AppConvert;
import com.huiminpay.dto.AppDTO;
import com.huiminpay.entity.App;
import com.huiminpay.entity.Merchant;
import com.huiminpay.mapper.AppMapper;
import com.huiminpay.mapper.MerchantMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author dupengfei
 * @date 2021/9/15 21:22
 * @Description:
 */
@Service
public class AppServiceImpl implements AppServiceApi {

    @Autowired
    private AppMapper appMapper;
    @Autowired
    private MerchantMapper merchantMapper;

    //添加应用    如果接口中的方法抛了异常，实现类重写的方法可以不用写抛异常(相当于抛了)
    @Override
    public AppDTO insertApp(Long merchantId, AppDTO appDTO) throws BusinessException {
        //必要参数校验
        if (merchantId == null || merchantId == 0 || appDTO == null) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_100101);
        }

        //校验商户是否存在，校验商户资质审核是否通过
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_200002);
        }
        if (!merchant.getAuditStatus().equals("2")) {  //0-未申请,1-已申请待审核,2-审核通过,3-审核拒绝
            BusinessExceptionUtil.cast(CommonErrorCode.E_200003);
        }

        //校验应用名称是否存在(工作中有个不成文的规定，一个方法不要超过100行,所以可以定义方法)
        boolean existAppName = isExistAppName(merchantId, appDTO.getAppName());
        if (existAppName) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_200004);
        }


        //最后一步才是添加应用
        //设置商户id
        appDTO.setMerchantId(merchantId);

        //服务器端生成并设置应用id
        String uuid = UUID.randomUUID().toString();
        appDTO.setAppId(uuid);

        App app = AppConvert.Instance.dto2App(appDTO);
        appMapper.insert(app);

        //可以把自动生成的主键设置到AppDTO中
        return AppConvert.Instance.App2dto(app);
    }


    //校验应用名称是否存在的方法。只根据appName查不行，因为不同的商户可以有相同的应用名称，商户id和appName是在一张表中的
    private boolean isExistAppName(Long merchantId, String appName) {
        //                                                           查哪张表     对表中的哪个字段查询，参数     对表中的哪个字段查询，参数
        Integer count = appMapper.selectCount(new LambdaQueryWrapper<App>().eq(App::getAppName, appName).eq(App::getMerchantId, merchantId));
        if (count > 0) {
            return true;
        }
        return false;
    }


    //根据自己的商户id查询本商户下的应用
    @Override
    public List<AppDTO> queryAppsByMerchantId(Long merchantId) throws BusinessException {
        //必要参数校验
        if (merchantId == null || merchantId == 0) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_110006);
        }

        //QueryWrapper查询的3种方式
        //非lambda的查询形式
        //List<App> apps = appMapper.selectList(new QueryWrapper<App>().eq("MERCHANT_ID", merchantId));
        //用lambda表达式的两种形式
        //List<App> apps2 = appMapper.selectList(new LambdaQueryWrapper<App>().eq(App::getMerchantId, merchantId));
        List<App> apps = appMapper.selectList(new QueryWrapper<App>().lambda().eq(App::getMerchantId, merchantId));


        List<AppDTO> appDTOS = AppConvert.Instance.listApp2ListAppDto(apps);
        return appDTOS;
    }

    //根据应用id查询应用的详细信息
    @Override
    public AppDTO queryAppByAppId(String appId) throws BusinessException {

        //必要参数校验
        if (appId == null) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_110006);
        }

        //不能用这个，这个是用主键查找的方式
        //App app = appMapper.selectById(appId);
        //用这个，这个是用自己生成的appid查找的方式
        App app = appMapper.selectOne(new LambdaQueryWrapper<App>().eq(App::getAppId, appId));
        if (app==null){
            BusinessExceptionUtil.cast(CommonErrorCode.E_NO_AUTHORITY);
        }

        AppDTO appDTO = AppConvert.Instance.App2dto(app);
        return appDTO;
    }
}
