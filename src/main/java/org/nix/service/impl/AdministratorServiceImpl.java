package org.nix.service.impl;
import org.nix.dao.impl.SysOrderDaoImpl;
import org.nix.dao.repositories.SysOrderJpa;
import org.nix.entity.SysOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PreDestroy;
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
        String conditions;
        if (fullMatch) {
            conditions = field + " = '" + content + "'";
        } else {
            conditions = field + " = '%" + content + "%'";
        }
        return sysOrderDao.list((page - 1) * size,size,order,sort,conditions);
    }
}
