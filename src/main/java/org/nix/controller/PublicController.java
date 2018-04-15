package org.nix.controller;

import org.nix.annotation.CurrentUser;
import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.common.sysenum.SysRoleEnum;
import org.nix.entity.SysUser;
import org.nix.service.impl.SysUserServiceImpl;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 * <p>
 * TODO: 公共接口
 */
@RestController
@RequestMapping(value = "/public")
public class PublicController {

    @Autowired
    private SysUserServiceImpl sysUserService;

    /**
     * todo: 用户登陆使用接口
     * @param account 用户账号
     * @param password 用户登陆密码
     * @return 处理结果
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnObject login(@RequestParam("account") String account,
                              @RequestParam("password") String password,
                              HttpServletRequest request) {

        SysUser sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setPassword(password);
        sysUserService.login(sysUser,request);

        return ReturnUtil.success(null , sysUser);
    }

    /**
     * todo: 修改账户密码
     * @param password 修改后的密码
     * @param request 客户端请求对象
     * @return 处理结果
     */
    @RequestMapping(value = "/updatePassword")
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    public ReturnObject updateUser(@RequestParam("password")String password,
                                   HttpServletRequest request){

        sysUserService.updatePassword(password,request);
        return ReturnUtil.success(null,null);
    }

}
