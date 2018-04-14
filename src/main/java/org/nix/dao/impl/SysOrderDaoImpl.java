package org.nix.dao.impl;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.nix.dao.base.HibernateSession;
import org.nix.entity.City;
import org.nix.entity.SysOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Kiss
 * @date 2018/04/11 18:09
 */
@Repository
public class SysOrderDaoImpl extends HibernateSession {
    /**
     * 分页查找订单列表（模糊查找）
     * @param offset
     * @param limit
     * @param order
     * @param sort
     * @return
     * */
    public List<SysOrder> list(Integer offset, Integer limit, String order, String sort,String conditions) {
        String hql = "select * from sys_order";
        if (conditions != null && !conditions.isEmpty()) {
            hql += " where " + conditions;
        }
        if (order != null && !order.isEmpty()) {
            hql += " order by " + order + " " + (sort.isEmpty() ? " asc" : sort);
        }
        if (offset != null && offset != 0) {
            hql += " limit " + offset + "," + limit;
        }
        Query query = entityManager.createNativeQuery(hql,SysOrder.class);
        return query.getResultList();
    }
}
