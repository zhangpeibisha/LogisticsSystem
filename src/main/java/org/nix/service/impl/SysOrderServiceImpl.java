package org.nix.service.impl;

import org.apache.log4j.Logger;
import org.nix.Exception.LoginErrorException;
import org.nix.Exception.OrderProcessFlowException;
import org.nix.Exception.PaymentException;
import org.nix.common.constant.SysManger;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.common.sysenum.SysOrderEnum;
import org.nix.dao.repositories.CityJpa;
import org.nix.dao.repositories.SysOrderJpa;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.City;
import org.nix.entity.OrderWays;
import org.nix.entity.SysOrder;
import org.nix.entity.SysUser;
import org.nix.util.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 * <p>
 * TODO: 订单业务服务类
 */
@Service
public class SysOrderServiceImpl {

    private Logger logger = Logger.getLogger(SysOrderServiceImpl.class);

    @Autowired
    private SysOrderJpa sysOrderJpa;
    @Autowired
    private CityJpa cityJpa;
    @Autowired
    private SysUserJpa sysUserJpa;
    @Autowired
    private SysUserServiceImpl sysUserService;

    /**
     * todo: 生成订单服务,此时用户并没有付钱
     * 由于此时用户才定订单，因此发货时间不确定，因此到达起始时间应该为付账
     * 后并且发货时设置
     * <p>
     * 如果发生异常，订单生成将失败
     *
     * @param sysOrder 装载了订单的一些基础信息
     * @param request  用户请求
     */
    @Transactional
    public void createOrder(SysOrder sysOrder, HttpServletRequest request) {

        SysUser sysUser = (SysUser) request
                .getSession()
                .getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());

        if (sysUser == null) {
            logger.info("用户未登陆，下订单失败");
            throw new LoginErrorException();
        }

        sysOrder.setCreateTime(new Date());
        // 设置订单状态
        sysOrder.setOrderStatus(SysOrderEnum.ORDER_PENDING_PAYMENT);
        // 设置订单用户
        sysOrder.setSysUser(sysUser);
        // 设置订单路径
        setOrderWay(sysOrder);

        sysOrderJpa.save(sysOrder);
        logger.info(sysUser.getId() + "在" + new Date() + "下订单" + sysOrder.getId());
    }

    /**
     * todo:执行订单支付操作
     *
     * @param sysOrder 要处理的订单
     * @param request  用户请求
     * @see org.nix.Exception.PaymentException 用户余额不足时抛出
     * @see LoginErrorException session会话中无用户信息时将抛出异常
     * @see OrderProcessFlowException 如果这个订单的处理上级不符合规则，则抛出异常
     */
    @Transactional
    public void paymentOrder(SysOrder sysOrder, HttpServletRequest request) {

        if (!sysOrder.getOrderStatus().equals(SysOrderEnum.ORDER_PENDING_PAYMENT)) {
            logger.info("订单处理流程异常，当前订单状态为：" +
                    sysOrder.getOrderStatus().getMessage() +
                    "不能进行支付处理");
            throw new OrderProcessFlowException();
        }
        SysUser user = (SysUser) request
                .getSession().getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());

        if (user == null) {
            logger.info("用户未登陆，支付失败");
            throw new LoginErrorException();
        }

        user = sysUserJpa.findSysUserByAccount(user.getAccount());

        // 支付订单
        sysUserService.paymentService(user.getAccount(), SysManger.FINANCE_ACCOUNT, sysOrder.getCost());

        //更改订单状态
        sysOrder.setOrderStatus(SysOrderEnum.ORDER_PAID_NO_SHIPPED);

        logger.info(user.getId() + "已经支付订单" + sysOrder.getId() + "，正在准备派送到" + sysOrder.getEndCity().getCityName());

    }

    /**
     * todo: 运送中的订单，调用此方法将让订单进入下一个城市
     *
     * @param sysOrder 需要处理的订单
     */
    @Transactional
    public void shippedOrder(SysOrder sysOrder) {

        City current = findNextCityBySysOrder(sysOrder);

        // 表示订单已经到达了目的地
        if (current == null) {
            sysOrder.setCurrentCity(sysOrder.getEndCity());
            sysOrder.setTimeOfArrival(new Date());
            sysOrder.setOrderStatus(SysOrderEnum.ORDER_ARRIVALS);
            sysOrderJpa.saveAndFlush(sysOrder);
            return;
        }

        sysOrder.setCurrentCity(current);
        sysOrder.setTimeOfArrival(new Date());
        sysOrder.setOrderStatus(SysOrderEnum.ORDER_BEING_SHIPPED);
        sysOrderJpa.saveAndFlush(sysOrder);

    }

    /**
     * todo: 用户签收订单
     *
     * @param sysOrder 需要处理的订单
     * @see LoginErrorException 如果用户未登陆，则抛出异常，执行签收失败
     */
    @Transactional
    public void SysUserSign(SysOrder sysOrder, HttpServletRequest request) {
        SysUser sysUser = (SysUser) request
                .getSession().getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        if (sysUser == null)
            throw new LoginErrorException();

        sysOrder.setTimeOfArrival(new Date());
        sysOrder.setOrderStatus(SysOrderEnum.ORDER_SIGN);
        sysOrderJpa.saveAndFlush(sysOrder);
        logger.info(sysUser.getId() + "已经签收订单" + sysOrder.getId());
    }


    /**
     * 设置订单的最短路劲经过的城市
     */
    private void setOrderWay(SysOrder order) {
        List<City> cities = (List<City>) Graph.getMinWay(order.getStartCity(), order.getEndCity(), cityJpa.findAll()).get("way");
        List<OrderWays> ways = new ArrayList<>();
        if (cities != null) {
            for (City city : cities) {
                OrderWays orderWays = new OrderWays();
                orderWays.setCity(city);
                orderWays.setFinish(false);
                orderWays.setExpectedDate(new Date());
                orderWays.setArriveDate(null);
                ways.add(orderWays);
            }
        }
        order.setOrderWays(ways);
    }

    /**
     * todo: 查找订单的下一个城市
     *
     * @param sysOrder 需要处理的订单
     * @return 订单将进入的下一个城市，如果为最终城市，那么返回Null
     */
    public City findNextCityBySysOrder(SysOrder sysOrder) {
        List<OrderWays> cities = sysOrder.getOrderWays();
        City currentCity = sysOrder.getCurrentCity();
        Assert.notNull(cities,"订单的最短路径不能为空");
        for (int i = 0;i < cities.size();i ++) {
            if (cities.get(i).getCity().equals(currentCity)) {
                if (i == cities.size() - 1) {
                    return null;
                } else {
                    return cities.get(i + 1).getCity();
                }
            }
        }
        return null;
    }
}
