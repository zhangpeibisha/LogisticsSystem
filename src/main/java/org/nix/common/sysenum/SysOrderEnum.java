package org.nix.common.sysenum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 * Arrivals
 * TODO: 为了表示订单所处状态的枚举类
 */
public enum SysOrderEnum {

    ORDER_PENDING_PAYMENT("001","订单已经生成，但是用户未付款"),
    ORDER_PAID_NO_SHIPPED("002","订单已支付，但是还没有开始运送"),
    ORDER_BEING_SHIPPED("003","订单正在运送"),
    ORDER_ARRIVALS("004","订单到达目的地"),
    ORDER_SIGN("005","订单签收")
    ;

    SysOrderEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }

    //订单状态代码
    private String status;
    //状态码描述
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return " [ status:" + status + " message:" + message +" ]";
    }
}
