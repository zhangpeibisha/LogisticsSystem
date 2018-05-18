package org.nix.dao.repositories;

import org.hibernate.criterion.Order;
import org.nix.entity.OrderEvaluation;
import org.nix.entity.SysOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 *
 * TODO: 订单底层业务接口
 */
@Service
public interface SysOrderJpa extends JpaRepository<SysOrder,Integer> {

    @Query(nativeQuery = true,
    value = "select COUNT(*) from sys_order where sys_user = ?1 order by id ASC")
    long orderCount(int userId);

    @Query(nativeQuery = true,
    value = "select * from sys_order where sys_user = ?1 order by asc id")
    List<Order> findSysOrderByUserId(int userId);

}
