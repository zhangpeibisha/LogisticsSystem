package org.nix.dto.order;

import org.apache.tomcat.jni.User;
import org.nix.dto.base.AbstractResultDto;
import org.nix.entity.City;
import org.nix.entity.SysOrder;
import org.nix.entity.SysUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/5/19.
 */
public class ResultOrderList extends AbstractResultDto {

    private List<SysOrder> orderList;

    public ResultOrderList(List<SysOrder> orderList) {
        this.orderList = orderList;
    }

    @Override
    public void handleInfo() {

        List<SysOrder> orderList = new ArrayList<>();
        for (SysOrder sysOrder : this.orderList) {
            SysOrder sysOrder1 = new SysOrder();
            sysOrder1.setCost(sysOrder.getCost());
            sysOrder1.setCreateTime(sysOrder.getCreateTime());
            sysOrder1.setId(sysOrder.getId());

            SysUser sysUser = new SysUser();
            sysUser.setAccount(sysOrder.getSysUser().getAccount());
            sysOrder1.setSysUser(sysUser);


            City city = new City();
            city.setCityName(sysOrder.getCurrentCity().getCityName());
            city.setId(sysOrder.getCurrentCity().getId());
            sysOrder1.setCurrentCity(city);
            sysOrder1.setNode(sysOrder.getNode());
            sysOrder1.setTimeOfArrival(sysOrder.getTimeOfArrival());
            City endCity = new City();
            endCity.setCityName(sysOrder.getEndCity().getCityName());
            endCity.setId(sysOrder.getEndCity().getId());
            sysOrder1.setEndCity(endCity);

            City startCity = new City();
            startCity.setCityName(sysOrder.getStartCity().getCityName());
            startCity.setId(sysOrder.getStartCity().getId());
            sysOrder1.setStartCity(startCity);

            sysOrder1.setOrderStatus(sysOrder.getOrderStatus());
            orderList.add(sysOrder1);
        }
        this.orderList = orderList;
    }


    public List<SysOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<SysOrder> orderList) {
        this.orderList = orderList;
    }
}
