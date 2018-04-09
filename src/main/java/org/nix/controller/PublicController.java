package org.nix.controller;

import org.nix.common.ReturnObject;
import org.nix.service.impl.SysUserServiceImpl;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnObject login(@RequestParam("account") String account,
                              @RequestParam("password") String password) {

        sysUserService.findUserByAccountAndPassword(account, password);

        return ReturnUtil.success(null , null);
    }


}
