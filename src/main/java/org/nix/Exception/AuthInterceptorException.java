package org.nix.Exception;

import org.nix.Exception.base.BaseException;
import org.nix.common.sysenum.ErrorCodeEnum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/15.
 */
public class AuthInterceptorException extends BaseException{

    public AuthInterceptorException() {
        super(ErrorCodeEnum.ERROR_SYS_AUTHINTERCEPTOR);
    }
}
