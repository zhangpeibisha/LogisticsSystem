package org.nix.controller;

import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.dao.impl.SysUserDao;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.entity.SysOrder;
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
        sysUserService.register(sysUser);
        return ReturnUtil.success(null, null);
    }

    /**
     * todo: 用户下订单接口
     * @param price 订单价格
     * @param startCity 订单起始城市
     * @param endCity 订单目的地城市
     * @param currentCity 订单当前城市
     * @param node 订单备注
     * @param cargoName 运送货物的名字
     * @param cargoPrice 运送货物的价值
     * @param request 用户请求对象
     * @return 处理结果
     */
    @RequestMapping(value = "/publishOrders" , method = RequestMethod.POST)
    @LoginRequired
    public ReturnObject publishOrders(@RequestParam("price")double price,
                                      @RequestParam("startCity")String startCity,
                                      @RequestParam("endCity")String endCity,
                                      @RequestParam("currentCity")String currentCity,
                                      @RequestParam("node")String node,
                                      @RequestParam("cargoName")String cargoName,
                                      @RequestParam("cargoPrice")String cargoPrice,
                                      HttpServletRequest request){




        return ReturnUtil.success(null,null);

    }


}
