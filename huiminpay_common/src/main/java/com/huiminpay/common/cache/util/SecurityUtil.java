package com.huiminpay.common.cache.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/***
 * 获取当前登录用户信息
 * 前端配置token，后续每次请求并通过Header方式发送至后端
 */
public class SecurityUtil {

    //测试使用
    public static Long getMerchantId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
                .getRequest();
        String jsonToken = request.getHeader("authorization");
        //从头信息中获取的就是下面的令牌
        //jsonToken = "Bearer eyJtZXJjaGFudElkIjoxMzc2NDM4NTc5NDYyNzM3OTIxLCJ1c2VyX25hbWUiOiLlvKDlhYjnlJ8iLCJtb2JpbGUiOiIxMzA4MTkzNjY2NiJ9";
        if (StringUtils.isEmpty(jsonToken) || !jsonToken.startsWith("Bearer ")) {
            throw new RuntimeException("token is not as expected");
        }
        //这是去掉 Bearer+空格
        jsonToken = jsonToken.substring(7);
        jsonToken = EncryptUtil.decodeUTF8StringBase64(jsonToken);
        JSONObject jsonObject = JSON.parseObject(jsonToken);
        //根据key获取值，并转为Long类型
        return jsonObject.getLong("merchantId");
    }

    /**
     * 根据租户ID查询商户ID
     * @param tenantId
     * @return
     */
/*	public static Long getMerchantId(Long tenantId){
		MerchantService merchantService = ApplicationContextHelper.getBean(MerchantService.class);
		MerchantDTO merchant = merchantService.queryMerchantByTenantId(tenantId);
		Long merchantId = null;
		if(merchant!=null){
			merchantId = merchant.getId();
		}
		return merchantId;
	}*/

    /**
     * 获取当前登录用户信息
     * @return
     */
	/*public static LoginUser getUser() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		if (servletRequestAttributes != null) {
			HttpServletRequest request = servletRequestAttributes.getRequest();

			Object jwt = request.getAttribute("jsonToken");
			if (jwt instanceof LoginUser) {
				return (LoginUser) jwt;
			}
		}
		return new LoginUser();
	}*/

	/*	token 明文格式
		{
			"mobile": "",
				"payload": "",
				"client_id": "merchant-platform",
				"user_name": "",
				"merchantId":"1196392632578809858"
		}*/
    public static void main(String[] args) {
        String jsonToken = "Bearer eyJtZXJjaGFudElkIjoxNDI4NjM5Mzc4MjQ0MTYxNTM4LCJ1c2VyX25hbWUiOiJzdHJpbmciLCJtb2JpbGUiOiIxODUzMDAzNjc0MyJ9";
        if (StringUtils.isEmpty(jsonToken) || !jsonToken.startsWith("Bearer ")) {
            throw new RuntimeException("token is not as expected");
        }

        jsonToken = jsonToken.substring(7);
        jsonToken = EncryptUtil.decodeUTF8StringBase64(jsonToken);
        JSONObject jsonObject = JSON.parseObject(jsonToken);
        System.out.println("merchantId==================="+jsonObject.getString("merchantId"));
    }

}
