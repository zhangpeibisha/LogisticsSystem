package org.nix.controller;

import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.common.sysenum.SysRoleEnum;
import org.nix.dao.impl.SysUserDao;
import org.nix.dao.repositories.SysRoleJpa;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.City;
import org.nix.entity.SysOrder;
import org.nix.entity.SysRole;
import org.nix.entity.SysUser;
import org.nix.service.impl.SysUserServiceImpl;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 * TODO: 这个类为普通用户使用的接口
 */
@RestController
@RequestMapping(value = "/generalUser")
public class GeneralUserController {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private SysRoleJpa sysRoleJpa;

    /**
     * todo: 用于普通用户注册接口，默认无角色信息，需要后期添加角色信息
     * 如果账户重复：将抛出唯一约束异常
     * 如果密码或者账户长度小于6或者大于18：将抛出字段长度不符合约束异常
     *
     * @param account  用户注册账号
     * @param password 用户注册密码
     * @return 如果注册成功，将返回给前端成功的信息
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ReturnObject register(@RequestParam("account") String account,
                                 @RequestParam("password") String password) {
        SysUser sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setPassword(password);
        SysRole sysRole = sysRoleJpa.findSysRoleByRoleName(SysRoleEnum.ROLE_GENERAL.getValue());
        sysUser.setSysRole(sysRole);
        sysUserService.register(sysUser);
        return ReturnUtil.success("注册成功", null);
    }




}
