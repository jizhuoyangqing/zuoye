package com.huiminpay.handle;

import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.domain.RestErrorResponse;
import com.huiminpay.common.cache.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dupengfei
 * @date 2021/9/7 14:17
 * @Description: 全局异常处理类
 */
@ControllerAdvice //告诉Spring容器这是全局异常处理类，并且是针对Controller层切面的增强
public class GlobleExceptionHandle {

    //抛了异常就会给这个类的这个方法（可以拦截所有的异常）
    @ExceptionHandler(value = Exception.class) //指定该全局异常处理类的这个方法来处理异常
    @ResponseBody   //不进行页面跳转，返回给前端
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)    //告诉浏览器状态 500
    public RestErrorResponse processException(Exception exception) {
        //判断异常是否为自定义异常类
        if (exception instanceof BusinessException) {
            BusinessException be = (BusinessException) exception;

            //获取信息，封装进RestErrorResponse
            RestErrorResponse errorResponse = new RestErrorResponse(be.getErrorCode().getCode(), be.getErrorCode().getDesc());
            return errorResponse;
        }

        //非自定义的异常类型
        return new RestErrorResponse(CommonErrorCode.UNKNOWN.getCode(),CommonErrorCode.UNKNOWN.getDesc());
    }

}
