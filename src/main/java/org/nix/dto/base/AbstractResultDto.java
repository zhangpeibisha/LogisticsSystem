package org.nix.dto.base;

/**
 * Create by zhangpe0312@qq.com on 2018/5/19.
 */
public abstract class AbstractResultDto implements BaseResultDto{

    @Override
    public BaseResultDto result() {
        handleInfo();
        return this;
    }

}
