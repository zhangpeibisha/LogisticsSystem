package org.nix.controller;

import org.nix.Exception.SelectObjectException;
import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.dao.repositories.OrderEvaluationJpa;
import org.nix.dao.repositories.SysOrderJpa;
import org.nix.dto.order.ResultOrderInfoDto;
import org.nix.entity.City;
import org.nix.entity.OrderEvaluation;
import org.nix.entity.SysOrder;
import org.nix.service.impl.CityServiceImpl;
import org.nix.service.impl.OrderEvaluationServiceImpl;
import org.nix.service.impl.SysOrderServiceImpl;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by zhangpe0312@qq.com on 2018/4/9.
 * todo: 订单模块接口
 */
@RestController
@RequestMapping(value = "/order")
public class SysOrderController {

    @Autowired
    private CityServiceImpl cityService;

    @Autowired
    private SysOrderServiceImpl sysOrderService;

    @Autowired
    private SysOrderJpa sysOrderJpa;

    @Autowired
    private OrderEvaluationServiceImpl orderEvaluationService;

    @Autowired
    private OrderEvaluationJpa orderEvaluationJpa;

    /**
     * todo: 用户下订单接口
     * 如果没有备注需要返回一个空字符串
     *
     * @param sysOrder 用户下单信息
     * @param request  用户请求对象
     * @return 处理结果
     */
    @RequestMapping(value = "/publishOrder", method = RequestMethod.POST)
    @LoginRequired
    public ReturnObject publishOrder(@ModelAttribute SysOrder sysOrder,
                                     HttpServletRequest request) {

        sysOrderService.createOrder(sysOrder, request);

        return ReturnUtil.success(null, null);

    }

    /**
     * todo: 用户支付接口
     *
     * @param order_id 订单id
     * @param request  用户请求
     * @return 操作结果
     * @see SelectObjectException() 如果订单未找到将抛出
     */
    @RequestMapping(value = "/paymentOrder", method = RequestMethod.POST)
    @LoginRequired
    public ReturnObject paymentOrder(@RequestParam("order_id") int order_id, HttpServletRequest request) {
        SysOrder sysOrder = sysOrderJpa.findOne(order_id);
        if (sysOrder == null) {
            throw new SelectObjectException();
        }
        sysOrderService.paymentOrder(sysOrder, request);
        return ReturnUtil.success(null, null);
    }

    /**
     * todo: 查看指定订单的详细信息
     *
     * @param order_id 需要处理订单的id
     * @return 操作回馈
     */
    @RequestMapping(value = "/viewOrderInfo", method = RequestMethod.POST)
    @LoginRequired
    public ReturnObject viewOrderInfo(@RequestParam("order_id") int order_id) {
        SysOrder sysOrder = sysOrderJpa.findOne(order_id);
        if (sysOrder == null) {
            throw new SelectObjectException();
        }
        return ReturnUtil.success(new ResultOrderInfoDto(sysOrder));
    }

    /**
     * todo: 订单签收操作
     *
     * @param order_id 需要处理订单的id
     * @param request  用户请求
     * @return 操作回馈
     */
    @RequestMapping(value = "/signOrder", method = RequestMethod.POST)
    @LoginRequired
    public ReturnObject signOrder(@RequestParam("order_id") int order_id, HttpServletRequest request) {
        SysOrder sysOrder = sysOrderJpa.findOne(order_id);
        if (sysOrder == null) {
            throw new SelectObjectException();
        }
        sysOrderService.SysUserSign(sysOrder, request);
        return ReturnUtil.success(null, null);
    }

    /**
     * todo: 根据指定条件分页查询订单列表
     *
     * @param page      当前页
     * @param size      每页多少行数据
     * @param order     顶顶那id
     * @param sort      是否排序
     * @param field     数据库字段
     * @param content   查询内容
     * @param fullMatch 是否完全匹配
     * @param request   用户请求
     * @return 返回请求信息
     */
    @PostMapping("/orderListConditionalPaging")
    public ReturnObject orderListConditionalPaging(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "order", defaultValue = "id") String order,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "content", defaultValue = "") String content,
            @RequestParam(value = "fullMatch", defaultValue = "true") Boolean fullMatch,
            HttpServletRequest request) {
//        SysUser sysUser = (SysUser) request
//                .getSession()
//                .getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
//        if (!sysUser.isSysAdmin()) {
//            return ReturnUtil.fail(null,"权限不足",null);
//        }
        return ReturnUtil.success(cityService.list(page, size, order, sort, field, content, fullMatch));
    }

    /**
     * todo: 给与订单评价
     *
     * @param order_id 订单id
     * @param message  评价信息
     * @return 操作反馈
     */
    @PostMapping(value = "/orderEvaluation")
    public ReturnObject orderEvaluation(@RequestParam("order_id") int order_id,
                                        @RequestParam("message") String message,
                                        HttpServletRequest request) {

        SysOrder sysOrder = sysOrderJpa.findOne(order_id);
        if (sysOrder == null) {
            throw new SelectObjectException();
        }

        orderEvaluationService.OrderEvaluation(sysOrder, message, request);

        return ReturnUtil.success(null, null);
    }

    /**
     * todo: 对订单评价进行回复
     *
     * @param evaluation_id 被评价信息id
     * @param message       回复信息
     * @param request       请求对象
     * @return 操作反馈
     */
    @PostMapping(value = "/evaluationReply")
    public ReturnObject evaluationReply(@RequestParam("evaluation_id") int evaluation_id,
                                        @RequestParam("message") String message,
                                        HttpServletRequest request) {

        OrderEvaluation orderEvaluation = orderEvaluationJpa.findOne(evaluation_id);
        orderEvaluationService.evaluationReply(orderEvaluation, message, request);
        return ReturnUtil.success(null, null);
    }

}
