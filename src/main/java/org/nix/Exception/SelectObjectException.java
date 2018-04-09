package org.nix.Exception;

import org.nix.Exception.base.BaseException;
import org.nix.common.sysenum.ErrorCodeEnum;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 *
 * todo: 用户表示从数据库中查询信息，但是并未查询到信息而抛出的错误
 */
public class SelectObjectException extends BaseException{

    public SelectObjectException() {
        super(ErrorCodeEnum.ERROR_DB_SELECT);
    }
}
