package org.nix.dao.repositories;

import org.nix.entity.CityDisPrimaryKey;
import org.nix.entity.OrderEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 * todo: 订单评论底层接口
 */
@Service
public interface OrderEvaluationJpa extends JpaRepository<OrderEvaluation,Integer> {
}
