package org.nix.common.sysenum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 * <p>
 * TODO: 系统错误代码及描述枚举类
 * -1 未知错误
 * 100-200 数据库自定义异常
 * 200-300 web异常
 * 300-400 服务异常
 */
public enum ErrorCodeEnum {
    ERROR_WEB_EXEEPTION(201, "web的异常"),
    ERROR_DB_SELECT(101, "查询持久态对象失败"),
    ERROR_SYS_UNKONWN(-1,"未知错误，该错误还没有被确认"),
    ERROR_SYS_REQUEST_METHON_NOT_SUPPORTED(202,"请求方法错误，没有找到相应的请求方法接口");

    private Integer errorCode;
    private String message;

    ErrorCodeEnum(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
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
