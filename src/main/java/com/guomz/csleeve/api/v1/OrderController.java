package com.guomz.csleeve.api.v1;

import com.guomz.csleeve.core.LocalUser;
import com.guomz.csleeve.dto.OrderDto;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.Order;
import com.guomz.csleeve.service.OrderChecker;
import com.guomz.csleeve.service.OrderServiceImpl;
import com.guomz.csleeve.vo.OrderIdVo;
import com.guomz.csleeve.vo.OrderPureVo;
import com.guomz.csleeve.vo.OrderSimplifyVo;
import com.guomz.csleeve.vo.PageDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;
    @Value("${guo.pay-time-limit}")
    private Integer payTimeLimit;

    /**
     * 下单
     * @param orderDto
     * @return
     */
    @PostMapping("")
    public OrderIdVo placeOrder(@RequestBody OrderDto orderDto){
        Long userId = LocalUser.getUser().getId();
        //订单校验
        OrderChecker orderChecker = orderService.isOk(userId, orderDto);
        //下订单
        Long orderId = orderService.placeOrder(userId, orderDto, orderChecker);
        return new OrderIdVo(orderId);
    }

    /**
     * 查询待支付订单
     * @param start
     * @param count
     * @return
     */
    @RequestMapping("/status/unpaid")
    public PageDozer<Order, OrderSimplifyVo> getUnpaidOrder(@RequestParam(value = "start", defaultValue = "0") Integer start,
                                                            @RequestParam(value = "count", defaultValue = "10") Integer count){
        Long userId = LocalUser.getUser().getId();
        Page<Order> orderPage = orderService.getUnpaid(userId, start, count);
        PageDozer<Order, OrderSimplifyVo> orderSimplifyVoPageDozer = new PageDozer<>(orderPage, OrderSimplifyVo.class);
        //给每一个个体赋值订单超时时间
        orderSimplifyVoPageDozer.getItems().forEach(item -> {
            ((OrderSimplifyVo)item).setPeriod(payTimeLimit);
        });
        return orderSimplifyVoPageDozer;
    }

    /**
     * 根据状态查询订单，不包括待支付
     * @param status
     * @param start
     * @param count
     * @return
     */
    @RequestMapping("/by/status/{status}")
    public PageDozer<Order, OrderSimplifyVo> getByStatus(@PathVariable("status") Integer status, @RequestParam(value = "start", defaultValue = "0") Integer start,
                                                         @RequestParam(value = "count", defaultValue = "10") Integer count){
        Long userId = LocalUser.getUser().getId();
        Page<Order> orderPage = orderService.getByStatus(userId, status, start, count);
        PageDozer<Order, OrderSimplifyVo> orderSimplifyVoPageDozer = new PageDozer<>(orderPage, OrderSimplifyVo.class);
        //给每一个个体赋值订单超时时间
        orderSimplifyVoPageDozer.getItems().forEach(item -> {
            ((OrderSimplifyVo)item).setPeriod(payTimeLimit);
        });
        return orderSimplifyVoPageDozer;
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    @RequestMapping("/detail/{id}")
    public OrderPureVo getOrderDetail(@PathVariable("id") Long id){

        Long userId = LocalUser.getUser().getId();
        Order order = orderService.getOrderDetail(userId, id);
        if (order == null){
            throw new ParameterException(50009);
        }
        return new OrderPureVo(order, payTimeLimit);
    }
}
