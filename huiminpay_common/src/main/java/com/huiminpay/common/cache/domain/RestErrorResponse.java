package com.huiminpay.common.cache.domain;

import lombok.Data;

/**
 * rest风格的错误信息响应类
 */
@Data
public class RestErrorResponse {

    private Integer errorCode;

    private String errorMsg;

    public RestErrorResponse(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
