package com.huiminpay.controller;

import com.huiminpay.api.AppServiceApi;
import com.huiminpay.common.cache.util.SecurityUtil;
import com.huiminpay.dto.AppDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dupengfei
 * @date 2021/9/15 23:31
 * @Description:
 */

//@Api(value = "商户应用API接口", description = "商户应用API接口,包含增删改查功能")
@Api(tags = "应用模块管理，应用添加，查询等功能")
@RestController
@RequestMapping("/app")
public class AppController {
    @Reference
    private AppServiceApi appServiceApi;

    @ApiOperation("添加应用")
    //                 参数名          参数描述           参数类型             这个参数从什么路径传过来的  是否必填项
    @ApiImplicitParam(name = "appDTO",value = "应用信息",dataType = "AppDTO",paramType = "body",required = true)
    @PostMapping("/my/apps")
    public AppDTO insertApp(@RequestBody AppDTO appDTO) {

        //用工具类获取令牌中的merchantId
        Long merchantId = SecurityUtil.getMerchantId();

        //执行插入操作
        AppDTO dto = appServiceApi.insertApp(merchantId, appDTO);
        return dto;
    }


    @ApiOperation("根据商户id查询本商户下的应用")
    @GetMapping("/my/apps")
    public List<AppDTO> queryAppsByMerchantId() {

        //用工具类获取令牌中的merchantId
        Long merchantId = SecurityUtil.getMerchantId();

        //执行插入操作
        List<AppDTO> appDTOS = appServiceApi.queryAppsByMerchantId(merchantId);
        return appDTOS;
    }


    @ApiOperation("根据应用id查询应用的详细信息")
    @ApiImplicitParam(name = "appId",value = "应用id",dataType = "String",paramType = "path",required = true)
    @GetMapping("/my/apps/{appId}") //名字一样就不加@PathVariable("appId")了
    public AppDTO queryAppByAppId(@PathVariable String appId) {
        AppDTO appDTO = appServiceApi.queryAppByAppId(appId);
        return appDTO;
    }

}
