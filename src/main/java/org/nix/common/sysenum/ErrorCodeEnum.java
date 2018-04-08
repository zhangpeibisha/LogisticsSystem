package org.nix.common.sysenum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 *
 * TODO: 系统错误代码及描述枚举类
 */
public enum  ErrorCodeEnum {
   ERROR_WEB_EXEEPTION("001","web的异常");

    private String errorCode;
    private String message;

    ErrorCodeEnum(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorCodeEnum{" +
                "errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
