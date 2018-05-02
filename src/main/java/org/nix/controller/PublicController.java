package org.nix.controller;

import org.nix.Exception.AuthInterceptorException;
import org.nix.annotation.CurrentUser;
import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.common.interceptors.AuthInterceptor;
import org.nix.common.sysenum.SessionKeyEnum;
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
import javax.servlet.http.HttpSession;


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
                              @RequestParam("grade") String grade,
                              HttpServletRequest request) {

        SysUser sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setPassword(password);
        sysUserService.login(sysUser,request);
        HttpSession session = request.getSession();
        sysUser = (SysUser) session.getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        if (!sysUser.getSysRole().getRoleName().equals(grade)) {
            // 如果权限不对，则清空会话
            session.invalidate();
            // 抛出权限不足异常
            throw new AuthInterceptorException();
        }
        return ReturnUtil.success(null , sysUser);
    }

    /**
     * todo: 修改账户密码
     * @param password 修改后的密码
     * @param request 客户端请求对象
     * @return 处理结果
     */
    @RequestMapping(value = "/updatePassword")
    @LoginRequired({SysRoleEnum.ROLE_PUBLIC})
    public ReturnObject updateUser(@RequestParam("password")String password,
                                   @RequestParam("oldPassword")String oldPassword,
                                   HttpServletRequest request){

        sysUserService.updatePassword(password,request,oldPassword);
        return ReturnUtil.success(null,null);
    }

    /**
     * 退出系统
     * @param request
     * @return
     */
    @RequestMapping(value = "/exitSystem")
    @LoginRequired({SysRoleEnum.ROLE_PUBLIC})
    public ReturnObject exitSystem(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return ReturnUtil.success(null,null);
    }
}
