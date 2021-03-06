package org.nix.controller;

import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.common.sysenum.SysRoleEnum;
import org.nix.dto.order.ResultOrderInfoDto;
import org.nix.dto.resources.ResultURIlistDto;
import org.nix.entity.SysOrder;
import org.nix.entity.SysUser;
import org.nix.service.impl.AdministratorServiceImpl;
import org.nix.service.impl.ResourcesServiceImpl;
import org.nix.service.impl.SysOrderServiceImpl;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 * <p>
 * TODO: 管理员接口
 */
@RestController
@RequestMapping(value = "/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorServiceImpl administratorService;


    @Autowired
    private ResourcesServiceImpl resourcesService;

    @Autowired
    private SysOrderServiceImpl sysOrderService;

    /**
     * 查看订单列表
     */
    @PostMapping("/orderList")
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    public ReturnObject orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "20") Integer size,
                                  @RequestParam(value = "order", defaultValue = "id") String order,
                                  @RequestParam(value = "sort", defaultValue = "ASC") String sort,
                                  @RequestParam(value = "field", defaultValue = "") String field,
                                  @RequestParam(value = "content", defaultValue = "") String content,
                                  @RequestParam(value = "fullMatch", defaultValue = "true") Boolean fullMatch,
                                  HttpServletRequest request) {

        SysUser sysUser = (SysUser) request
                .getSession()
                .getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        if (!sysUser.isSysAdmin()) {
            return ReturnUtil.fail(null, "权限不足", null);
        }
        return ReturnUtil.success(administratorService.list(page, size, order, sort, field, content, fullMatch));
    }

    /**
     * 订单受理
     */
    @PostMapping(value = "/orderHandler")
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    public ReturnObject orderHandler(@RequestParam("id") Integer id, HttpServletRequest request) {
        SysOrder order = sysOrderService.findById(id);
        if (administratorService.orderHandler(order, (SysUser) request
                .getSession()
                .getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey()))) {
            return ReturnUtil.success("受理成功");
        }
        return ReturnUtil.fail("受理失败");
    }


    // 下面两个接口在本系统中不用，权限控制换作注解方式进行

    /**
     * todo: 获取到系统全部的RUI数据
     * @param request 客户端请求
     * @return URI数据
     */
    @PostMapping(value = "/listViewURI")
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    public ReturnObject listViewURI(HttpServletRequest request) {

        return ReturnUtil.success(new ResultURIlistDto(resourcesService.listGetURI(request)));
    }

    /**
     * todo: 将接口信息导入到数据库中 --- 不适用
     * @param request
     * @return
     */
    @PostMapping(value = "/listURIToDB")
    @LoginRequired(SysRoleEnum.ROLE_ADMINISTRATOR)
    public ReturnObject listURIToDB(HttpServletRequest request){

        resourcesService.listURIToDB(resourcesService.listGetURI(request));

        return ReturnUtil.success(null);
    }

}
