package com.huiminpay.common.cache.domain;

public class BusinessException extends RuntimeException{

    private ErrorCode errorCode;

    public BusinessException(){}

    public BusinessException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
