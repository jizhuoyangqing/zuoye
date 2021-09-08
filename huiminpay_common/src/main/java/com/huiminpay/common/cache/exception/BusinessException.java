package com.huiminpay.common.cache.exception;

import com.huiminpay.common.cache.domain.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dupengfei
 * @date 2021/9/7 13:53
 * @Description: 自定义异常类
 */
@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;
}
