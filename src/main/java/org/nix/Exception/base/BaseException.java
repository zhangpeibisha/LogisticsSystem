package org.nix.Exception.base;

import org.nix.common.sysenum.ErrorCodeEnum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 */
public class BaseException extends RuntimeException {

    private Integer errorCode;

    private String errorMessage;

    public BaseException(ErrorCodeEnum errorCodeEnum) {
        this.errorCode = errorCodeEnum.getErrorCode();
        this.errorMessage = errorCodeEnum.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
