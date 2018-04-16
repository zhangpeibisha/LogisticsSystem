package org.nix.dto.order;

import org.nix.dto.base.BaseResultDto;
import org.nix.entity.OrderEvaluation;
import org.nix.entity.SysOrder;

import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/12.
 * todo: 返回单个订单信息接口
 */
public class ResultOrderInfoDto implements BaseResultDto{

    private SysOrder sysOrder;
    private List<OrderEvaluation> orderEvaluations;

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
        orderEvaluations = sysOrder.getOrderEvaluation();
    }

    public SysOrder getSysOrder() {
        return sysOrder;
    }

    public void setSysOrder(SysOrder sysOrder) {
        this.sysOrder = sysOrder;
    }

    public List<OrderEvaluation> getOrderEvaluations() {
        return orderEvaluations;
    }

    public void setOrderEvaluations(List<OrderEvaluation> orderEvaluations) {
        this.orderEvaluations = orderEvaluations;
    }
}
