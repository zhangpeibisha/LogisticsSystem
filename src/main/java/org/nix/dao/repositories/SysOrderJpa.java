package org.nix.dao.repositories;

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


}
