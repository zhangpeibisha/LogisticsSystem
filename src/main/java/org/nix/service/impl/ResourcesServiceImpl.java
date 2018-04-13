package org.nix.service.impl;

import org.nix.dao.repositories.SysResourcesJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Create by zhangpe0312@qq.com on 2018/4/13.
 * <p>
 * todo: 系统URI资源管理服务
 */
@Service
public class ResourcesServiceImpl {

    @Autowired
    private SysResourcesJpa sysResourcesJpa;

    /**
     * todo: 获取系统中所有的URI路径
     *
     * @param request 客户端请求
     * @return URI路径
     */
    public Set<String> listGetURI(HttpServletRequest request) {
        Set<String> result = new HashSet<String>();
        WebApplicationContext wc = (WebApplicationContext) request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        RequestMappingHandlerMapping bean = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        for (RequestMappingInfo rmi : handlerMethods.keySet()) {
            PatternsRequestCondition pc = rmi.getPatternsCondition();
            Set<String> pSet = pc.getPatterns();
            result.addAll(pSet);
        }
        return result;
    }

    /**
     * todo: 将路径经过处理后导入数据库
     * URI格式
     * {
     * "data": {
     * "uris": [
     * "/city/neighbor",
     * "/city/search",
     * "/order/orderEvaluation",
     * "/error",
     * "/public/updatePassword",
     * "/order/signOrder",
     * "/Administrator/listViewURI",
     * "/Administrator/orderHandler",
     * "/city/delete",
     * "/order/evaluationReply",
     * "/city/details/{id}",
     * "/order/paymentOrder",
     * "/Administrator/orderList",
     * "/public/login",
     * "/city/create",
     * "/order/orderListConditionalPaging",
     * "/order/publishOrder",
     * "/generalUser/register",
     * "/order/viewOrderInfo"
     * ]
     * },
     * "msg": "SUCCESS",
     * "status": 1
     * }
     *
     * @param uris uri集合
     */
    @Transactional
    public void listURIToDB(Set<String> uris) {

        String dis;
        String methon;

        Iterator iterator = uris.iterator();

        while (iterator.hasNext()){

        }
    }


}
