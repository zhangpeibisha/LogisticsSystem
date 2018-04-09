package org.nix.controller;

import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.entity.City;
import org.nix.entity.SysOrder;
import org.nix.service.impl.CityServiceImpl;
import org.nix.service.impl.SysOrderServiceImpl;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 * todo: 订单模块接口
 */
@RestController
public class SysOrderController {

    @Autowired
    private CityServiceImpl cityService;

    @Autowired
    private SysOrderServiceImpl sysOrderService;

    /**
     * todo: 用户下订单接口
     * 如果没有备注需要返回一个空字符串
     * @param price 订单价格
     * @param startCity 订单起始城市
     * @param endCity 订单目的地城市
     * @param currentCity 订单当前城市
     * @param node 订单备注
     * @param cargoName 运送货物的名字
     * @param cargoPrice 运送货物的价值
     * @param request 用户请求对象
     * @return 处理结果
     */
    @RequestMapping(value = "/publishOrders" , method = RequestMethod.POST)
    @LoginRequired
    public ReturnObject publishOrders(@RequestParam("price")double price,
                                      @RequestParam("startCity")String startCity,
                                      @RequestParam("endCity")String endCity,
                                      @RequestParam("currentCity")String currentCity,
                                      @RequestParam("node")String node,
                                      @RequestParam("cargoName")String cargoName,
                                      @RequestParam("cargoPrice")double cargoPrice,
                                      HttpServletRequest request){

        City start = cityService.findCityByCityName(startCity);
        City end = cityService.findCityByCityName(endCity);
        City current = cityService.findCityByCityName(currentCity);
        SysOrder sysOrder = new SysOrder();
        sysOrder.setCost(price);
        sysOrder.setCurrentCity(current);
        sysOrder.setEndCity(end);
        sysOrder.setStartCity(start);
        sysOrder.setNode(node);
        sysOrder.setCargoName(cargoName);
        sysOrder.setCargoPrice(cargoPrice);
        sysOrderService.createOrder(sysOrder,request);

        return ReturnUtil.success(null,null);

    }

}
