package org.nix.dto.order;

import org.nix.common.sysenum.SysOrderEnum;
import org.nix.dto.base.AbstractResultDto;
import org.nix.dto.base.BaseResultDto;
import org.nix.entity.SysOrder;

import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/5/2.
 */
public class ResultOrderStatistics extends AbstractResultDto {
    /**
     * 交易成功的订单
     */
    private int successOrderNumber = 0;
    /**
     * 等待发货的订单
     */
    private int waitDeliverNumber = 0;
    /**
     * 等待收货的订单
     */
    private int waitTakeNumber = 0;
    /**
     * 会员数量
     */
    private int memberNumber = 0;

    private List<SysOrder> sysOrderList;

    /**
     * 用户普通用户统计
     * @param sysOrderList
     */
    public ResultOrderStatistics(List<SysOrder> sysOrderList) {
        this.sysOrderList = sysOrderList;
    }

    /**
     * 用于管理员统计
     * @param memberNumber
     * @param sysOrderList
     */
    public ResultOrderStatistics(int memberNumber, List<SysOrder> sysOrderList) {
        this.memberNumber = memberNumber;
        this.sysOrderList = sysOrderList;
    }

    @Override
    public void handleInfo() {

        for (SysOrder sysOrder : sysOrderList) {
            if (sysOrder.getOrderStatus() == SysOrderEnum.ORDER_SIGN)
                successOrderNumber++;
            if (sysOrder.getOrderStatus() == SysOrderEnum.ORDER_PAID_NO_SHIPPED)
                waitDeliverNumber++;
            if (SysOrderEnum.ORDER_ARRIVALS == sysOrder.getOrderStatus())
                waitTakeNumber++;
        }
    }

    public int getSuccessOrderNumber() {
        return successOrderNumber;
    }

    public void setSuccessOrderNumber(int successOrderNumber) {
        this.successOrderNumber = successOrderNumber;
    }

    public int getWaitDeliverNumber() {
        return waitDeliverNumber;
    }

    public void setWaitDeliverNumber(int waitDeliverNumber) {
        this.waitDeliverNumber = waitDeliverNumber;
    }

    public int getWaitTakeNumber() {
        return waitTakeNumber;
    }

    public void setWaitTakeNumber(int waitTakeNumber) {
        this.waitTakeNumber = waitTakeNumber;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(int memberNumber) {
        this.memberNumber = memberNumber;
    }
}
