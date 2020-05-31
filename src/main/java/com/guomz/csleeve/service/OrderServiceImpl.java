package com.guomz.csleeve.service;

import com.guomz.csleeve.bo.PageBo;
import com.guomz.csleeve.core.money.IMoneyDiscount;
import com.guomz.csleeve.dto.OrderDto;
import com.guomz.csleeve.enums.CouponStatus;
import com.guomz.csleeve.enums.OrderStatus;
import com.guomz.csleeve.exception.http.NotFoundException;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.*;
import com.guomz.csleeve.repository.CouponRepository;
import com.guomz.csleeve.repository.OrderRepository;
import com.guomz.csleeve.repository.SkuRepository;
import com.guomz.csleeve.repository.UserCouponRepository;
import com.guomz.csleeve.utils.CommonUtil;
import com.guomz.csleeve.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl {

    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private IMoneyDiscount iMoneyDiscount;
    @Autowired
    private OrderRepository orderRepository;
    //订单支付时间，超过此时间未支付则订单失效
    @Value("${guo.pay-time-limit}")
    private Integer payTimeLimit;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 下订单
     *
     * @param userId
     * @param orderDto
     */
    @Transactional
    public Long placeOrder(Long userId, OrderDto orderDto, OrderChecker orderChecker) {
        //下订单
        String orderNo = OrderUtil.makeOrderNo();
        Order order = new Order(userId, orderDto, orderChecker, orderNo, OrderStatus.UNPAID);
        Calendar now = Calendar.getInstance();
        //计算下单时间与失效时间
        order.setPlacedTime(now.getTime());
        now.add(Calendar.SECOND, payTimeLimit);
        order.setExpiredTime(now.getTime());
        orderRepository.save(order);
        //减库存
        reduceStock(orderChecker.getOrderSkuList());
        //核销优惠券
        if (orderDto.getCouponId() != null){
            writeOffCoupon(userId, orderDto.getCouponId(), order.getId());
        }
        //将订单信息写入redis
        this.sendToRedis(userId, order.getId(), orderDto.getCouponId());
        return order.getId();
    }

    /**
     * 将订单信息写入redis
     * @param userId
     * @param orderId
     * @param couponId
     */
    private void sendToRedis(Long userId, Long orderId, Long couponId){
        String keyStr = userId + "," + orderId + "," + couponId;
        try {
            stringRedisTemplate.opsForValue().set(keyStr, "1", this.payTimeLimit, TimeUnit.SECONDS);
        } catch (Exception e) {
            //异常不要抛出，抛出会导致事务回滚
            e.printStackTrace();
        }
    }

    /**
     * 查询待支付的订单
     *
     * @param userId
     * @param start
     * @param count
     * @return
     */
    public Page<Order> getUnpaid(Long userId, int start, int count) {
        PageBo pageBo = CommonUtil.convertStartToPage(start, count);
        Pageable pageable = PageRequest.of(pageBo.getPageNum(), pageBo.getCount(), Sort.by("createTime").descending());
        return orderRepository.findByUserIdAndExpiredTimeGreaterThanAndStatus(userId, new Date(), OrderStatus.UNPAID.value(), pageable);
    }

    /**
     * 按照状态查询订单，不包含未支付的
     * @param userId
     * @param status
     * @param start
     * @param count
     * @return
     */
    public Page<Order> getByStatus(Long userId, int status, int start, int count) {
        PageBo pageBo = CommonUtil.convertStartToPage(start, count);
        Pageable pageable = PageRequest.of(pageBo.getPageNum(), pageBo.getCount(), Sort.by("createTime").descending());
        if (status == OrderStatus.All.value()) {
            return orderRepository.findByUserId(userId, pageable);
        } else {
            return orderRepository.findByUserIdAndStatus(userId, status, pageable);
        }
    }

    /**
     * 查询订单详情
     * @param userId
     * @param orderId
     * @return
     */
    public Order getOrderDetail(Long userId, Long orderId){
        return orderRepository.findByUserIdAndId(userId, orderId);
    }


    /**
     * 减库存操作
     *
     * @param orderSkuList
     */
    private void reduceStock(List<OrderSku> orderSkuList) {
        orderSkuList.stream().forEach(orderSku -> {
            int result = skuRepository.reduceStockById(orderSku.getId(), orderSku.getCount());
            if (result != 1) {
                throw new ParameterException(50003);
            }
        });
    }

    /**
     * 核销优惠券
     *
     * @param userId
     * @param couponId
     */
    private void writeOffCoupon(Long userId, Long couponId, Long orderId) {
        int result = userCouponRepository.writeOffCoupon(userId, couponId, orderId, CouponStatus.USED.getValue());
        if (result != 1) {
            throw new ParameterException(40012);
        }
    }

    /**
     * 校验订单是否合法
     *
     * @param userId
     * @param orderDto
     */
    public OrderChecker isOk(Long userId, OrderDto orderDto) {
        //验证金额是否大于零
        if (orderDto.getFinalTotalPrice().compareTo(new BigDecimal("0")) < 0) {
            throw new ParameterException(10001);
        }
        //获取涉及的全部的sku
        List<Long> idList = orderDto.getSkuInfoDtoList().stream().map(skuInfoDto -> {
            return skuInfoDto.getId();
        }).collect(Collectors.toList());
        List<Sku> skuList = skuRepository.findAllByIdIn(idList);

        //如果有优惠券则取出优惠券信息
        Long couponId = orderDto.getCouponId();
        CouponChecker couponChecker = null;
        if (couponId != null) {
            //检查优惠券是否存在
            Optional<Coupon> couponOptional = couponRepository.findById(couponId);
            couponOptional.orElseThrow(() -> new NotFoundException(40002));
            //检查用户是否领取过优惠券，即userCoupon的存在
            Optional<UserCoupon> userCouponOptional = userCouponRepository.findByUserIdAndCouponId(userId, couponId, CouponStatus.AVAILABLE.getValue());
            userCouponOptional.orElseThrow(() -> new NotFoundException(50006));
            couponChecker = new CouponChecker(couponOptional.get(), iMoneyDiscount);
        }
        //进行订单校验
        OrderChecker orderChecker = new OrderChecker(orderDto, skuList, couponChecker);
        orderChecker.isOk();
        return orderChecker;
    }
}
