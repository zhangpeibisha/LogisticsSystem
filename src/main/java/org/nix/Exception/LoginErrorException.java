package org.nix.Exception;

import org.nix.Exception.base.BaseException;
import org.nix.common.sysenum.ErrorCodeEnum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/10.
 * todo: 检测当前会话中是否有用户登陆信息
 */
public class LoginErrorException extends BaseException{

    public LoginErrorException() {
        super(ErrorCodeEnum.ERROR_SYS_LOGIN);
    }
}
