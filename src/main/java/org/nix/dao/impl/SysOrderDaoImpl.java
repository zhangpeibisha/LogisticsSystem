package org.nix.dao.impl;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
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
            hql += " order by " + sort + " " + (sort.isEmpty() ? " asc" : order);
        }
        if (offset != null && offset != 0) {
            hql += " limit " + offset + "," + limit;
        }
        Query query = entityManager.createNativeQuery(hql,SysOrder.class);
        return query.getResultList();
    }

    /**
     *
     * @param userId 用户id
     * @param curr 当前页
     * @param limit 每页多少行
     * @return
     */
    public List<SysOrder> findOrderPageByUserId(int userId,int curr,int limit){
        if (curr<1)
            curr = 1;
        curr = (curr-1)*limit;
        limit = curr + limit;
        String sql = "select * from sys_order where sys_user = userId LIMIT currPage,limitPage";
        sql = sql.replaceAll("userId", String.valueOf(userId))
                .replaceAll("currPage", String.valueOf(curr))
                .replaceAll("limitPage", String.valueOf(limit));
        Query query = entityManager.createNativeQuery(sql);
       return query.getResultList();
    }
}
