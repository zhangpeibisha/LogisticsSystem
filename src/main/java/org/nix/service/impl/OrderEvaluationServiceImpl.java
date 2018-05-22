package org.nix.service.impl;

import org.nix.Exception.LoginErrorException;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.dao.impl.OrderEvaluationDaoImpl;
import org.nix.dao.repositories.OrderEvaluationJpa;
import org.nix.dao.repositories.SysOrderJpa;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.OrderEvaluation;
import org.nix.entity.SysOrder;
import org.nix.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Create by zhangpe0312@qq.com on 2018/4/11.
 * todo: 评论服务类
 */
@Service
public class OrderEvaluationServiceImpl {

    @Autowired
    private OrderEvaluationJpa orderEvaluationJpa;

    @Autowired
    private SysUserJpa sysUserJpa;

    @Autowired
    private SysOrderJpa sysOrderJpa;

    @Autowired
    private OrderEvaluationDaoImpl orderEvaluationDao;

    /**
     * todo: 对订单进行评论
     * 由于是物流订单，因此只有用户自己本人评价，而评论回复只能是管理员回复
     *
     * @param sysOrder 需要评论的订单
     * @param message  用户给与的该订单的评价
     * @param request  用户的请求
     */
    @Transactional
    public void OrderEvaluation(SysOrder sysOrder, String message, HttpServletRequest request) {

        SysUser sysUser = (SysUser) request
                .getSession().getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        if (sysUser == null)
            throw new LoginErrorException();

        OrderEvaluation orderEvaluation = new OrderEvaluation();
//        orderEvaluation.setCreateTime(new Date());
        orderEvaluation.setEvaluation(message);
        orderEvaluation.setSysUser(sysUser);
        orderEvaluation.setSysOrder(sysOrder);
        orderEvaluationDao.save(orderEvaluation);
    }

//    /**
//     * todo: 回复评价信息
//     *
//     * @param orderEvaluation 被回复的订单
//     * @param message         回复的信息
//     * @param request         用户请求
//     */
//    @Transactional
//    public void evaluationReply(OrderEvaluation orderEvaluation, String message, HttpServletRequest request) {
//
//        SysUser sysUser = (SysUser) request
//                .getSession().getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
//        if (sysUser == null) {
//            throw new LoginErrorException();
//        }
//
//        //构建新的评论回复
//        OrderEvaluation newEvaluation = new OrderEvaluation();
//        newEvaluation.setSysUser(sysUser);
//        newEvaluation.setEvaluation(message);
//        newEvaluation.setCreateTime(new Date());
//        newEvaluation.setSysOrder(orderEvaluation.getSysOrder());
//
//        //更新评论信息
//        orderEvaluation.getNextEvaluations().add(newEvaluation);
//        orderEvaluationJpa.saveAndFlush(orderEvaluation);
//    }

    /**
     * todo: 获取该订单的所有评价信息
     *
     * @param sysOrder 需要查看评价的订单
     * @return 该订单的评价列表
     */
    public List<OrderEvaluation> findAllOrderEvaluationByOrder(SysOrder sysOrder) {
        return sysOrderJpa.findOne(sysOrder.getId()).getOrderEvaluation();
    }

}
