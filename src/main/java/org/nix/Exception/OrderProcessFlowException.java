package org.nix.Exception;

import org.nix.Exception.base.BaseException;
import org.nix.common.sysenum.ErrorCodeEnum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/10.
 * todo: 订单业务处理异常，流程不和规定业务流程吻合
 */
public class OrderProcessFlowException extends BaseException {

    public OrderProcessFlowException() {
        super(ErrorCodeEnum.ERROR_SYS_ORDER_HANDLE_FLOW);
    }
}
