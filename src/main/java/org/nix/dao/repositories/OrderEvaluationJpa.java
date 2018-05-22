package org.nix.dao.repositories;

import org.nix.entity.CityDisPrimaryKey;
import org.nix.entity.OrderEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 * todo: 订单评论底层接口
 */
@Service
@Transactional
public interface OrderEvaluationJpa extends JpaRepository<OrderEvaluation,Integer> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM order_evaluation WHERE order_evaluation.sys_order = ?1")
   List<OrderEvaluation> findByOrderId(int orderId);
}
