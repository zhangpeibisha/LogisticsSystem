package org.nix.service.impl;

import org.apache.log4j.Logger;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.common.sysenum.SysOrderEnum;
import org.nix.dao.repositories.SysOrderJpa;
import org.nix.entity.SysOrder;
import org.nix.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

        if (sysUser == null){
            logger.info("用户未登陆，下订单失败");
            throw new NullPointerException("session中没有key:" + SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        }

        sysOrder.setCreateTime(new Date());
        sysOrder.setOrderStatus(SysOrderEnum.ORDER_PENDING_PAYMENT);
        sysOrder.setSysUser(sysUser);
        sysOrderJpa.save(sysOrder);
        logger.info(sysUser.getId() + "在" + new Date() + "下订单" + sysOrder.getId());
    }

}
