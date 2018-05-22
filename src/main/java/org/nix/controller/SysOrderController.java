package org.nix.controller;

import io.jsonwebtoken.Header;
import org.nix.Exception.SelectObjectException;
import org.nix.annotation.LoginRequired;
import org.nix.common.ReturnObject;
import org.nix.common.sysenum.SessionKeyEnum;
import org.nix.common.sysenum.SysOrderEnum;
import org.nix.common.sysenum.SysRoleEnum;
import org.nix.dao.impl.SysOrderDaoImpl;
import org.nix.dao.repositories.OrderEvaluationJpa;
import org.nix.dao.repositories.SysOrderJpa;
import org.nix.dao.repositories.SysUserJpa;
import org.nix.dto.order.ResultOrderInfoDto;
import org.nix.dto.order.ResultOrderList;
import org.nix.dto.order.ResultOrderStatistics;
import org.nix.entity.*;
import org.nix.service.impl.CityServiceImpl;
import org.nix.service.impl.OrderEvaluationServiceImpl;
import org.nix.service.impl.SysOrderServiceImpl;
import org.nix.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @Autowired
    private SysUserJpa sysUserJpa;

    @Autowired
    private SysOrderDaoImpl sysOrderDao;
    /**
     * todo: 用户下订单接口
     * <p>
     * 使用角色： 用户
     * <p>
     * 如果没有备注需要返回一个空字符串
     *
     * @param sysOrder 用户下单信息
     * @param request  用户请求对象
     * @return 处理结果
     */
    @RequestMapping(value = "/publishOrder", method = RequestMethod.POST)
    @LoginRequired(SysRoleEnum.ROLE_GENERAL)
    public ReturnObject publishOrder(@ModelAttribute SysOrder sysOrder,
                                     @RequestParam("startplaceId") int startplace,
                                     @RequestParam("endplaceId") int endplace,
                                     HttpServletRequest request) {
        sysOrder.setOrderStatus(SysOrderEnum.ORDER_PAID_NO_SHIPPED);
        City startCity = cityService.findById(startplace);
        City endCity = cityService.findById(endplace);
        City currCity = cityService.findById(startplace);
        sysOrder.setStartCity(startCity);
        sysOrder.setEndCity(endCity);
        sysOrder.setCurrentCity(currCity);

        sysOrderService.createOrder(sysOrder, request);

        return ReturnUtil.success(null, null);

    }

    /**
     * todo: 用户支付接口
     * 使用角色： 用户
     *
     * @param order_id 订单id
     * @param request  用户请求
     * @return 操作结果
     * @see SelectObjectException() 如果订单未找到将抛出
     */
    @RequestMapping(value = "/paymentOrder", method = RequestMethod.POST)
    @LoginRequired(SysRoleEnum.ROLE_GENERAL)
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
     * 使用角色: 管理员能够查看全部的，用户只能查看自己的
     *
     * @param order_id 需要处理订单的id
     * @return 操作回馈
     */
    @RequestMapping(value = "/viewOrderInfo", method = RequestMethod.POST)
    @LoginRequired({SysRoleEnum.ROLE_GENERAL, SysRoleEnum.ROLE_ADMINISTRATOR})
    public ReturnObject viewOrderInfo(@RequestParam("order_id") int order_id) {
        SysOrder sysOrder = sysOrderJpa.findOne(order_id);
        if (sysOrder == null) {
            throw new SelectObjectException();
        }
        return ReturnUtil.success(new ResultOrderInfoDto(sysOrder));
    }

    /**
     * todo: 查看指定订单的详细信息
     * 使用角色: 管理员能够查看全部的，用户只能查看自己的
     *
     * @param order_id 需要处理订单的id
     * @return 操作回馈
     */
    @RequestMapping(value = "/ways", method = RequestMethod.POST)
    @LoginRequired({SysRoleEnum.ROLE_GENERAL, SysRoleEnum.ROLE_ADMINISTRATOR})
    public ReturnObject ways(@RequestParam("order_id") int order_id) {
        Map<String,Object> map = new HashMap<>();
        SysOrder sysOrder = sysOrderJpa.findOne(order_id);
        map.put("order",new ResultOrderInfoDto(sysOrder));
        List<OrderWays> ways = new ArrayList<>();
        for (OrderWays orderWays:sysOrder.getOrderWays()) {
            OrderWays way = new OrderWays();
            way.setExpectedDate(orderWays.getExpectedDate());
            way.setArriveDate(orderWays.getArriveDate());
            way.setFinish(orderWays.getFinish());
            way.setCity(new City(orderWays.getCity().getCityName()));
            ways.add(way);
        }
        map.put("way",ways);
        if (sysOrder == null) {
            throw new SelectObjectException();
        }
        return ReturnUtil.success(map);
    }

    /**
     * todo: 订单签收操作
     * 使用角色：管理员
     *
     * @param order_id 需要处理订单的id
     * @param request  用户请求
     * @return 操作回馈
     */
    @RequestMapping(value = "/signOrder", method = RequestMethod.POST)
    @LoginRequired(SysRoleEnum.ROLE_GENERAL)
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
     * 使用角色：管理员能够查看全部订单 用户只能查看自己的订单
     *
     * @param page      当前页
     * @param size      每页多少行数据
     * @param order     顶顶那id
     * @param sort      是否排序
     * @param field     数据库字段
     * @param content   查询订单状态
     * @param fullMatch 是否完全匹配
     * @param request   用户请求
     * @return 返回请求信息
     */
    @PostMapping("/orderListConditionalPaging")
    @LoginRequired(value = {SysRoleEnum.ROLE_GENERAL, SysRoleEnum.ROLE_ADMINISTRATOR})
    public Map<String,Object> orderListConditionalPaging(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "order", defaultValue = "id") String order,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "content", defaultValue = "") String content,
            @RequestParam(value = "fullMatch", defaultValue = "true") Boolean fullMatch,
            HttpServletRequest request) {
        System.out.println("查询字段 ："+ request.getQueryString());
        Map map = new HashMap();
        SysUser sysUser = (SysUser) request
                .getSession()
                .getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        System.out.println(!sysUser.isSysAdmin());
        if (!sysUser.isSysAdmin()) {
            System.out.println(sysUser);
            map.put("total", sysOrderService.count(sysUser.getId()));
            Map<String,Object> result = new HashMap<>();
            result.put("user_order_list",new ResultOrderList(sysOrderService.list(page,size,order,sort,"sys_user = " + sysUser.getId())).result());
            result.put("status",1);
            result.put("msg","返回用户信息列表");
            return result;
        }else{
            map.put("total", sysOrderService.count());
            Map<String,Object> result = new HashMap<>();
            result.put("user_order_list",new ResultOrderList(sysOrderService.list(page, size, order, sort, field, content, fullMatch)).result());
            result.put("status",1);
            result.put("msg","返回所有用户信息列表");
            return result;
//            return ReturnUtil.success(null, sysOrderService.list(page, size, order, sort, field, content, fullMatch), map);
        }
    }

    /**
     * todo: 给与订单评价
     * 使用角色：用户
     *
     * @param order_id 订单id
     * @param message  评价信息
     * @return 操作反馈
     */
    @PostMapping(value = "/orderEvaluation")
    @LoginRequired(value = SysRoleEnum.ROLE_GENERAL)
    public ReturnObject orderEvaluation(@RequestParam("order_id") int order_id,
                                        @RequestParam("message") String message,
                                        HttpServletRequest request) {

        SysOrder sysOrder = sysOrderJpa.findByOrderId(order_id);
        if (sysOrder == null) {
            throw new SelectObjectException();
        }

        orderEvaluationService.OrderEvaluation(sysOrder, message, request);

        return ReturnUtil.success(null, null);
    }

    /**
     * todo: 对订单评价进行回复
     * 使用角色： 管理员和用户
     *
     * @param evaluation_id 被评价信息id
     * @param message       回复信息
     * @param request       请求对象
     * @return 操作反馈
     */
    @PostMapping(value = "/evaluationReply")
    @LoginRequired(value = {SysRoleEnum.ROLE_ADMINISTRATOR, SysRoleEnum.ROLE_GENERAL})
    public ReturnObject evaluationReply(@RequestParam("evaluation_id") int evaluation_id,
                                        @RequestParam("message") String message,
                                        HttpServletRequest request) {

        OrderEvaluation orderEvaluation = orderEvaluationJpa.findOne(evaluation_id);
        orderEvaluationService.evaluationReply(orderEvaluation, message, request);
        return ReturnUtil.success(null, null);
    }

    /**
     * 返回订单统计信息
     * @param request
     * @return
     */
    @PostMapping(value = "/orderStatistics")
    @LoginRequired(value = {SysRoleEnum.ROLE_ADMINISTRATOR, SysRoleEnum.ROLE_GENERAL})
    public ReturnObject orderStatistics(HttpServletRequest request) {

        SysUser findUser = (SysUser) request.getSession().getAttribute(SessionKeyEnum.SESSION_KEY_CURRENT_USER.getKey());
        findUser = sysUserJpa.findSysUserByAccount(findUser.getAccount());
        if (findUser.getSysRole().getRoleName().equals(SysRoleEnum.ROLE_GENERAL.getValue())) {
            return ReturnUtil.success("返回订单统计信息", new ResultOrderStatistics(findUser.getSysOrder()).result());
        }
        if (findUser.getSysRole().getRoleName().equals(SysRoleEnum.ROLE_ADMINISTRATOR.getValue())) {
            return ReturnUtil.success("返回订单统计信息", new ResultOrderStatistics(sysUserJpa.CountAllGeneralUser(),findUser.getSysOrder()).result());
        }

        return ReturnUtil.fail(null);
    }

    @PostMapping("/shippedOrder")
    @LoginRequired(value = SysRoleEnum.ROLE_ADMINISTRATOR)
    public ReturnObject shippedOrder(@RequestParam("orderId")int orderId){
        sysOrderService.shippedOrder(sysOrderJpa.findOne(orderId));
        return ReturnUtil.success("处理成功","");
    }

    /**
     * 模拟物流运送
     * */
    @GetMapping("/transport")
    public Map<String,Object> autoTransport(@RequestParam("orderId")int orderId) {
        Map<String,Object> map = new HashMap<>();
        SysOrder sysOrder = sysOrderService.findById(orderId);
        List<OrderWays> orderWays = sysOrder.getOrderWays();
        City currentCity = sysOrder.getCurrentCity();
        for (int i = 0;i < orderWays.size();i ++) {
            if (currentCity.getCityName().equals(orderWays.get(i).getCity().getCityName())) {
                if (i != orderWays.size() - 1) {
                    sysOrder.setCurrentCity(orderWays.get(i + 1).getCity());
                    orderWays.get(i + 1).setFinish(true);
                    orderWays.get(i + 1).setArriveDate(new Date());
                    map.put("msg","订单已到达下一站");
                } else {
                    sysOrder.setOrderStatus(SysOrderEnum.ORDER_ARRIVALS);
                    map.put("msg","订单已到达目的地");
                    map.put("status",1);
                }
            }
        }
        sysOrder.setTimeOfArrival(new Date());
        sysOrderJpa.saveAndFlush(sysOrder);
        return map;
    }

    @GetMapping(value = "/findAllOrderEvaluationByOrderId")
    public Map<String,Object> findAllOrderEvaluationByOrderId(@RequestParam("orderId")int orderId){

        Map<String,Object> map = new HashMap<>();

        SysOrder sysOrder = sysOrderJpa.findByOrderId(orderId);
        if (sysOrder == null){
            map.put("status",-1);
            map.put("msg","查询失败，订单不存在");
            return map;
        }

        List<OrderEvaluation> orderEvaluations = sysOrder.getOrderEvaluation();
        map.put("status",1);
        map.put("msg","获取订单评价成功");
        map.put("data",orderEvaluations);
        return map;

    }
}
