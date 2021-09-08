package com.huiminpay.common.cache.exception;

import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.domain.ErrorCode;

/**
 * @author dupengfei
 * @date 2021/9/7 18:25
 * @Description:
 */
public class BusinessExceptionUtil {
    public static void cast(ErrorCode errorCode){
        throw new BusinessException(errorCode);
    }
}
