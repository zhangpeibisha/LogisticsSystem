package org.nix.service.impl;
import org.nix.common.sysenum.SysOrderEnum;
import org.nix.dao.impl.SysOrderDaoImpl;
import org.nix.dao.repositories.SysOrderJpa;
import org.nix.entity.SysOrder;
import org.nix.entity.SysUser;
import org.nix.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Kiss
 * @date 2018/04/11 17:38
 */
@Service
public class AdministratorServiceImpl {
    @Autowired
    private SysOrderDaoImpl sysOrderDao;
    @Autowired
    private SysOrderJpa sysOrderJpa;
    /**
     * 获取订单列表
     * */
    public List<SysOrder> list(Integer page, Integer size, String order, String sort, String field, String content,Boolean fullMatch) {
        return sysOrderDao.list(DataUtil.offset(page,size),size,order,sort,DataUtil.getConditions(field,content,fullMatch));
    }

    /**
     *
     * 受理订单
     * */
    @Transactional(rollbackFor = Exception.class)
    public boolean orderHandler(SysOrder order, SysUser sysUser) {
        if (sysUser.isSysAdmin()) {
            order.setOrderStatus(SysOrderEnum.ORDER_BEING_SHIPPED);
            order.setCurrentCity(order.getOrderWays().get(0).getCity());
            order.getOrderWays().get(0).setFinish(true);
            order.getOrderWays().get(0).setArriveDate(new Date());
            order.setTimeOfArrival(new Date());
            sysOrderJpa.saveAndFlush(order);
            return true;
        }
        return false;
    }
}
