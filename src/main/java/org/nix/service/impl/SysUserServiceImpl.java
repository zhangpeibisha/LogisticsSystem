package org.nix.service.impl;

import org.nix.Exception.SelectObjectException;
import org.nix.dao.impl.SysUserDao;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.SysUser;
import org.nix.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 *
 * TODO: 为系统用户提供一些服务
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser,Integer>{

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
     * @param account 用户账号
     * @param password 用户密码
     * @return 如果查询成功则返回用户信息，如果没有查询成功则抛出异常
     * @throws SelectObjectException
     */
    public SysUser findUserByAccountAndPassword(String account , String password) throws SelectObjectException{
        SysUser sysUser =  sysUserJpa.findSysUserByAccountAndPassword(account,password);
        if (sysUser == null)
            throw new SelectObjectException();
      return sysUser;
    }

    /**
     * todo: 一般用于用户注册时使用，如果重复注册将会抛出异常
     * @param sysUser 注册用户实体
     */
    @Transactional
    public void insertUser(SysUser sysUser){
        sysUserJpa.save(sysUser);
    }

    /**
     * todo: 一般用于更新用户信息,如果信息更新违反了数据库完整性，数据将混滚，更新失败
     * @param sysUser 信息更改后需要持久化的用户信息
     */
    @Transactional
    public void updateUser(SysUser sysUser){
        sysUserDao.updateSysUser(sysUser);
    }


}
