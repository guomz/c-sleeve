package com.guomz.csleeve.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guomz.csleeve.dto.OrderAddressDto;
import com.guomz.csleeve.dto.OrderDto;
import com.guomz.csleeve.enums.OrderStatus;
import com.guomz.csleeve.service.OrderChecker;
import com.guomz.csleeve.utils.CommonUtil;
import com.guomz.csleeve.utils.JsonConverterVer2;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Entity
// 因为order与MySQL关键字冲突所以采用这种写法
@Table(name = "`order`")
@NoArgsConstructor
public class Order extends BaseEntity{
    @Id
    private Long id;
    private String orderNo;
    private Long userId;
    // 订单包含的商品的原价总和
    private BigDecimal totalPrice;
    private Integer totalCount;
    // snap开头的为订单内容的快照信息
    private String snapImg;
    private String snapTitle;
    private String snapItems;
    private String snapAddress;
    // 微信支付相关
    private String prepayId;
    // 订单最终价格(包含折扣、优惠券等)
    private BigDecimal finalTotalPrice;
    private Integer status;
    //订单失效时间
    private Date expiredTime;
    //订单创建时间，为了时间计算精确不使用createTime
    private Date placedTime;

    public Order(Long userId, OrderDto orderDto, OrderChecker orderChecker, String orderNo, OrderStatus orderStatus){
        this.orderNo = orderNo;
        this.userId = userId;
        this.totalPrice = orderDto.getTotalPrice();
        this.totalCount = orderChecker.getTotalCount();
        this.snapImg = orderChecker.getSnapImg();
        this.snapTitle = orderChecker.getSnapTitle();
        this.setSnapItems(orderChecker.getOrderSkuList());
        this.setSnapAddress(orderDto.getOrderAddressDto());
        //注入快照信息
        this.setSnapItems(orderChecker.getOrderSkuList());
        this.finalTotalPrice = orderDto.getFinalTotalPrice();
        this.status = orderStatus.value();
    }

    /**
     * 判断订单是否应该被取消，通过当前订单状态与订单是否过期判断
     * @param period
     * @return
     */
    public Boolean needCancel(Long period){
        //先判断状态
        if (!this.status.equals(OrderStatus.UNPAID.value())){
            return true;
        }
        //判断是否过期
        return CommonUtil.isOutOfDate(this.placedTime, period);
    }

    public void setSnapItems(List<OrderSku> orderSkuList){
        this.snapItems = JsonConverterVer2.objectToJson(orderSkuList);
    }

    public List<OrderSku> getSnapItems(){
        List<OrderSku> orderSkuList = JsonConverterVer2.jsonToObejct(this.snapItems, new TypeReference<List<OrderSku>>() {
        });
        return orderSkuList;
    }

    public void setSnapAddress(OrderAddressDto orderAddressDto){
        this.snapAddress = JsonConverterVer2.objectToJson(orderAddressDto);
    }

    public OrderAddressDto getSnapAddress(){
        return JsonConverterVer2.jsonToObejct(this.snapAddress, new TypeReference<OrderAddressDto>() {
        });
    }
}
