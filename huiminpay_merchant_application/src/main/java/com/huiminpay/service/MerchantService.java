package com.huiminpay.service;

import com.huiminpay.api.MerchantServiceApi;
import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.exception.BusinessExceptionUtil;
import com.huiminpay.common.cache.util.PhoneUtil;
import com.huiminpay.common.cache.util.QiniuUtil;
import com.huiminpay.convert.MerchantRegisterConvert;
import com.huiminpay.dto.MerchantDto;
import com.huiminpay.vo.MerchantVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class MerchantService {
    @Reference
    MerchantServiceApi merchantServiceApi;

    @Autowired
    SmsService smsService;

    @Value("${qiniuyun.accessKey}")
    private String accessKey;
    @Value("${qiniuyun.secretKey}")
    private String secretKey;
    @Value("${qiniuyun.bucket}")
    private String bucket;
    @Value("${qiniuyun.domain}")
    private String domain;


    //令牌测试类使用的
    public MerchantDto queryMerchantById(Long merchantId) {
        return merchantServiceApi.queryMerchantById(merchantId);
    }



    //商户注册
    public MerchantVo registerMerchant(MerchantVo merchantVo) {

        //为了代码的健壮性，都要先处理异常
        if (merchantVo == null) {
            //用自己的工具类抛异常
            BusinessExceptionUtil.cast(CommonErrorCode.E_100101);
        }
        //校验关键参数
        if (StringUtils.isEmpty(merchantVo.getMobile())) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_100112);
        }
        if (StringUtils.isEmpty(merchantVo.getUsername())) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_100110);
        }
        if (StringUtils.isEmpty(merchantVo.getPassword())) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_100111);
        }
        //不为空就校验手机号的格式，使用工具类进行校验(用的正则表达式)
        if (!PhoneUtil.isMatches(merchantVo.getMobile())) {
            BusinessExceptionUtil.cast(CommonErrorCode.E_100109);
        }


        //校验验证码 这里抛异常后就不会往在下面走，不会完成注册
        smsService.verify(merchantVo.getVerifiykey(), merchantVo.getVerifiyCode());


        //商户的注册
        //MerchantDto merchantDto = new MerchantDto();

        //第一种用法 merchantDto.setMobile(merchantVo.getMobile());
        //第二种用法 BeanUtils.copyProperties(merchantVo,merchantDto);
        //第三种用法 原先还得new Merchant 现在不用new了，可以直接返回该对象
        MerchantDto merchantDto = MerchantRegisterConvert.INSTANCE.vo2dto(merchantVo);

        merchantServiceApi.registerMerchant(merchantDto);
        return merchantVo;
    }

    //商户资质申请中的图片文件上传
    public String uploadImg(MultipartFile multipartFile) {
        if (multipartFile==null){
            BusinessExceptionUtil.cast(CommonErrorCode.E_110006);
        }

        try {
            //这是获取图片的字节数组
            byte[] bytes = multipartFile.getBytes();
//            //如果工具类不返回文件名（我给改成可以返回文件名的了，所以这个只是演示）
//            String originalFilename = multipartFile.getOriginalFilename();
//            String fileName=UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
//            QiniuUtil.upload(accessKey, secretKey, bucket, fileName, bytes);
//            //返回云上文件的路径名
//            return domain+fileName;

            //null是自己不传名字，由文件内容的hash值生成，传了文件名就按自己传的文件名保存的
            //上传并返回系统生成的文件名
            String fileName = QiniuUtil.upload(accessKey, secretKey, bucket, null, bytes);
            //返回云上文件的路径名
            return domain+fileName;
        } catch (IOException e) {
            //打印的错误信息就会在打在大括号中
            log.error("上传文件异常：{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
