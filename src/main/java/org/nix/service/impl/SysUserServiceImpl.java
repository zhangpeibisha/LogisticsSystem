package org.nix.service.impl;

import org.nix.Exception.LoginErrorException;
import org.nix.Exception.PaymentException;
import org.nix.Exception.SelectObjectException;
import org.nix.annotation.CurrentUser;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.dao.impl.SysUserDao;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.SysUser;
import org.nix.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 * <p>
 * TODO: 为系统用户提供一些服务
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer> {

    @Autowired
    private SysUserJpa sysUserJpa;

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    protected void init() {
        jpaRepository = sysUserJpa;
    }

    /**
     * todo: 一般用于登陆时使用
     *
     * @param sysUser 装载了用户的账户和密码信息的对象
     *                如果查询成功则将用户信息存入session中，如果没有查询成功则抛出异常
     * @throws SelectObjectException
     */
    public void login(@CurrentUser SysUser sysUser, HttpServletRequest request) throws SelectObjectException {
        SysUser findUser = sysUserJpa
                .findSysUserByAccount(sysUser.getAccount());
        if (findUser == null || !findUser.getPassword().equals(sysUser.getPassword())) {
            throw new SelectObjectException();
        }

        findUser.setPassword(null);
        request.getSession().setAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey(), findUser);
    }

    /**
     * todo: 一般用于用户注册时使用，如果重复注册将会抛出异常
     *
     * @param sysUser 注册用户实体
     */
    @Transactional
    public void register(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        sysUserJpa.save(sysUser);
    }

    /**
     * todo: 一般用于更新用户信息,如果信息更新违反了数据库完整性，数据将混滚，更新失败
     *
     * @param password 用户更新后的密码
     */
    @Transactional
    public void updatePassword(String password , HttpServletRequest request) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        if (sysUser == null)
            throw new LoginErrorException();
        sysUser.setPassword(password);
        sysUserDao.updateSysUser(sysUser);
    }

    /**
     * 用户支付服务、收入服务
     * @see  org.nix.Exception.PaymentException 支付时余额不足将抛出
     * @see SelectObjectException 如果用户未查找到将抛出
     * @param paymentAccount 支付账号
     * @param collectAccount 收账账号
     * @param money 金额
     */
    @Transactional
    public void paymentService(String paymentAccount , String collectAccount , double money){

        SysUser payment = sysUserJpa.findSysUserByAccount(paymentAccount);
        SysUser collect = sysUserJpa.findSysUserByAccount(collectAccount);
        if (payment == null || collect == null)
            throw new SelectObjectException();

        double balance = payment.getBalance() - money;
        if (balance<0)
            throw new PaymentException();

        payment.setBalance(balance);
        collect.setBalance(collect.getBalance() + money);
        sysUserJpa.saveAndFlush(payment);
        sysUserJpa.saveAndFlush(collect);
    }

}
