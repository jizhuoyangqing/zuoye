package com.huiminpay.merchant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huiminpay.common.cache.util.EncryptUtil;
import com.huiminpay.common.cache.util.SecurityUtil;
import com.huiminpay.dto.MerchantDto;
import com.huiminpay.service.MerchantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;

/**
 * @author Administrator
 * @version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenTemp {

    @Autowired
    MerchantService merchantService;

    @Test
    public void createTestToken() {
                        //这个id就是数据库里真实的ID
        Long merchantId = 1376438579462737921L;//填写用于测试的商户id
        MerchantDto merchantDTO = merchantService.queryMerchantById(merchantId);
        JSONObject token = new JSONObject();
        token.put("mobile", merchantDTO.getMobile());
        token.put("user_name", merchantDTO.getUsername());
        token.put("merchantId", merchantId);

        //下面就是生成的令牌
        String jwt_token = "Bearer " + EncryptUtil.encodeBase64(JSON.toJSONString(token).getBytes());
        System.out.println(jwt_token);
        //Bearer eyJtZXJjaGFudElkIjoxMzc2NDM4NTc5NDYyNzM3OTIxLCJ1c2VyX25hbWUiOiLlvKDlhYjnlJ8iLCJtb2JpbGUiOiIxMzA4MTkzNjY2NiJ9

//        用SecurityUtil工具类进行获取其中的merchantId
//        System.out.println(SecurityUtil.getMerchantId());
    }
}
