package org.nix.common;

/**
 * 接口同意返回对象
 *
 * @author 11723*/
public class ReturnObject<T extends Object> {
    /**
     * 状态码
     * */
    private Integer status;
    /**
     * 消息
     */
    private String msg;
    /**
     * 返回内容
     */
    private T data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
