package org.nix.dao.impl;

import org.nix.dao.base.HibernateSession;
import org.nix.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 *
 * todo: 实现JPA不能实现的dao层功能
 */
@Service
public class SysUserDao extends HibernateSession{

    /**
     * todo: 更新用户信息
     * @param sysUser 更改信息后的用户
     */
    public void updateSysUser(SysUser sysUser){
        getSession().update(sysUser);
    }


}
