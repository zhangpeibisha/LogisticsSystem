package org.nix.common.interceptors;

import org.apache.log4j.Logger;
import org.nix.Exception.AuthInterceptorException;
import org.nix.Exception.LoginErrorException;
import org.nix.annotation.LoginRequired;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.common.sysenum.SysRoleEnum;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Create by zhangpe0312@qq.com on 2018/4/15.
 *
 * todo: 进行权限控制，通过LoginRequired标签检测用户是否有这个角色权限去访问这个接口
 */

public class AuthInterceptor implements HandlerInterceptor {

    private Logger logger = Logger.getLogger(AuthInterceptor.class);

    @Autowired
    private SysUserJpa userJpa;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if (o.getClass().isAssignableFrom(HandlerMethod.class)){

            LoginRequired loginRequired = ((HandlerMethod)o).getMethodAnnotation(LoginRequired.class);

            if(loginRequired == null)
                return true;

            HttpSession session = httpServletRequest.getSession();
            SysUser sysUser = (SysUser) session.getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
            if (sysUser == null)
                throw new LoginErrorException();

            sysUser = userJpa.findOne(sysUser.getId());

            // 如果验证通过则返回true
            if (loginRequired.value().getValue().equals(SysRoleEnum.ROLE_PUBLIC.getValue()) ||
                    loginRequired.value().getValue().equals(sysUser.getSysRole().getRoleName())){
                return true;
            }

            throw new AuthInterceptorException();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
