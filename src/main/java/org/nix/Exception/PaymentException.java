package org.nix.Exception;

import org.nix.Exception.base.BaseException;
import org.nix.common.sysenum.ErrorCodeEnum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/10.
 * todo: 用户支付异常
 */
public class PaymentException extends BaseException{

    public PaymentException() {
        super(ErrorCodeEnum.ERROR_SYS_PAYMENT);
    }
}
