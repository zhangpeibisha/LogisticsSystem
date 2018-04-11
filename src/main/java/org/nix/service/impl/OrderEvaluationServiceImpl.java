package org.nix.service.impl;

import org.nix.Exception.LoginErrorException;
import org.nix.common.constant.SysManger;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.dao.repositories.OrderEvaluationJpa;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.OrderEvaluation;
import org.nix.entity.SysOrder;
import org.nix.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * todo: 对订单进行评论
     * 由于是物流订单，因此只有用户自己本人评价，而评论回复只能是管理员回复
     *
     * @param sysOrder        需要评论的订单
     * @param orderEvaluation 用户给与的评价
     */
    @Transactional
    public void OrderEvaluation(SysOrder sysOrder, OrderEvaluation orderEvaluation) {

    }

    /**
     * todo:管理员回复用户的评价信息
     * 只能为系统管理员评价
     *
     * @param orderEvaluation 管理员的评论
     * @param request         用户请求
     */
    public void evaluationReply(OrderEvaluation orderEvaluation, HttpServletRequest request) {

        SysUser manger = (SysUser) request
                .getSession().getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        if (manger == null){
            throw new LoginErrorException();
        }
       sysUserJpa.findSysUserByAccount(SysManger.SYS_MANGER_ACCOUNT);
    }

}
