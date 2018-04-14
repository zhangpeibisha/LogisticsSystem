package org.nix.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.nix.dao.base.HibernateSession;
import org.nix.entity.City;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Kiss
 * @date 2018/04/12 17:51
 */
@Repository
public class CityDaoImpl extends HibernateSession {
    /**
     * 分页查找订单列表（模糊查找）
     * @param offset
     * @param limit
     * @param order
     * @param sort
     * @return
     * */
    public List<City> list(Integer offset, Integer limit, String order, String sort, String conditions) {
        String hql = "select * from city";
        if (conditions != null && !conditions.isEmpty()) {
            hql += " where " + conditions;
        }
        if (order != null && !order.isEmpty()) {
            hql += " order by " + order + " " + (sort.isEmpty() ? " asc" : sort);
        }
        if (offset != null && offset != -1) {
            hql += " limit " + offset + "," + limit;
        }
        Query query = entityManager.createNativeQuery(hql,City.class);
        return query.getResultList();
    }
}
