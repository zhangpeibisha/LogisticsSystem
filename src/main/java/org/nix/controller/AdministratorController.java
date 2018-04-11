package org.nix.controller;

import org.nix.common.ReturnObject;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.entity.SysUser;
import org.nix.service.impl.AdministratorServiceImpl;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by zhangpe0312@qq.com on 2018/4/8.
 *
 * TODO: 管理员接口
 */
@RestController
@RequestMapping(value = "/Administrator")
public class AdministratorController {
    @Autowired
    private AdministratorServiceImpl administratorService;
    /**
     * 查看订单列表
     * */
    @PostMapping("/orderList")
    public ReturnObject orderList(@RequestParam(value = "page",defaultValue = "1")  Integer page,
                                  @RequestParam(value = "size",defaultValue = "20") Integer size,
                                  @RequestParam(value = "order",defaultValue = "id") String order,
                                  @RequestParam(value = "sort",defaultValue = "ASC") String sort,
                                  @RequestParam(value = "field",defaultValue = "") String field,
                                  @RequestParam(value = "content",defaultValue = "") String content,
                                  @RequestParam(value = "fullMatch",defaultValue = "true") Boolean fullMatch,
                                  HttpServletRequest request) {
        SysUser sysUser = (SysUser) request
                .getSession()
                .getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        if (!sysUser.isSysAdmin()) {
            return ReturnUtil.fail(null,"权限不足",null);
        }
        return ReturnUtil.success(administratorService.list(page,size,order,sort,field,content,fullMatch));
    }
}
