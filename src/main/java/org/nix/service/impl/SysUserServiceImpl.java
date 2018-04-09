package org.nix.service.impl;

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
        if (findUser == null || !findUser.getPassword().equals(sysUser.getPassword()))
            throw new SelectObjectException();

        sysUser.setPassword(null);
        request.getSession().setAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey(), sysUser);
    }

    /**
     * todo: 一般用于用户注册时使用，如果重复注册将会抛出异常
     *
     * @param sysUser 注册用户实体
     */
    @Transactional
    public void register(SysUser sysUser) {
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
            throw new NullPointerException("session中没有key:"+SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        sysUser.setPassword(password);
        sysUserDao.updateSysUser(sysUser);
    }


}
