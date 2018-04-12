package org.nix.dto.order;

import org.nix.dto.base.BaseResultDto;
import org.nix.entity.SysOrder;

/**
 * Create by zhangpe0312@qq.com on 2018/4/12.
 * todo: 返回单个订单信息接口
 */
public class ResultOrderInfoDto implements BaseResultDto{

    private SysOrder sysOrder;

    public ResultOrderInfoDto(SysOrder sysOrder) {
        this.sysOrder = sysOrder;
    }

    @Override
    public BaseResultDto result() {
        handleInfo();
        return this;
    }

    @Override
    public void handleInfo() {

    }

    public SysOrder getSysOrder() {
        return sysOrder;
    }

    public void setSysOrder(SysOrder sysOrder) {
        this.sysOrder = sysOrder;
    }
}
