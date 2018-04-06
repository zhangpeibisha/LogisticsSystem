package org.nix.Exception;

/**
 * 返回异常
 * */
public class WebException extends Exception{
    /**
     *  错误码
     */

    private Integer code;
    /**
     * 错误信息
     */

    private String message;

    public WebException() {
        code = -1;
        message = "null";
    }

    public WebException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
