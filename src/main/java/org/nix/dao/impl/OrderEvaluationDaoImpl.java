package org.nix.dao.impl;

import org.nix.dao.base.HibernateSession;
import org.nix.entity.OrderEvaluation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

/**
 * Create by zhangpe0312@qq.com on 2018/5/22.
 */
@Repository
@Transactional
public class OrderEvaluationDaoImpl extends HibernateSession {

    public boolean save(OrderEvaluation orderEvaluation){
        String sql = "INSERT order_evaluation(create_time,evaluation,sys_order,sys_user) \n" +
                "VALUES(NOW(),'message',sys_order_id,sys_user_id)";
        sql = sql.replace("message",orderEvaluation.getEvaluation())
                .replace("sys_order_id",orderEvaluation.getSysOrder().getId()+"")
                .replace("sys_user_id",orderEvaluation.getSysUser().getId()+"");

        System.out.println("sql:\n"+sql);

       Query query =  entityManager.createNativeQuery(sql);

        return query.executeUpdate()>0?true:false;
    }



}
