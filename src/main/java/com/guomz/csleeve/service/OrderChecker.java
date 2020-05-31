package com.guomz.csleeve.service;

import com.guomz.csleeve.bo.SkuOrderBo;
import com.guomz.csleeve.dto.OrderDto;
import com.guomz.csleeve.dto.SkuInfoDto;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.OrderSku;
import com.guomz.csleeve.model.Sku;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 校验订单
 */
public class OrderChecker {

    private OrderDto orderDto;
    private List<Sku> skuList;
    private CouponChecker couponChecker;
    //写入订单的快照信息
    @Getter
    private List<OrderSku> orderSkuList;

    public OrderChecker(OrderDto orderDto, List<Sku> skuList, CouponChecker couponChecker){
        this.orderDto = orderDto;
        this.skuList = skuList;
        this.couponChecker = couponChecker;

    }

    /**
     * 验证订单是否合法的主方法
     */
    public void isOk(){
        //判断是否存在下架商品
        compareMount(orderDto.getSkuInfoDtoList(), this.skuList);
        List<SkuOrderBo> skuOrderBoList = new ArrayList<>();
        BigDecimal serverTotalPrice = new BigDecimal("0");
        for (int i = 0; i < this.skuList.size(); i ++){
            Sku sku = skuList.get(i);
            SkuInfoDto skuInfoDto = orderDto.getSkuInfoDtoList().get(i);
            //库存是否还剩余
            isOutOfStock(sku);
            //是否超出库存
            isOverStock(skuInfoDto, sku);
            skuOrderBoList.add(new SkuOrderBo(sku, skuInfoDto));
            //计算总价格
            serverTotalPrice.add(calculateServerPrice(skuInfoDto, sku));
            this.orderSkuList.add(new OrderSku(skuInfoDto, sku));
        }
        //校验无优惠券情况下价格是否一致
        totalPriceisOk(orderDto.getTotalPrice(), serverTotalPrice);
        //优惠券若存在则进行优惠券校验
        if (couponChecker != null){
            //优惠券是否过期
            couponChecker.isOk();
            //优惠券是否满足使用条件
            couponChecker.canBeUsed(skuOrderBoList, serverTotalPrice);
            //优惠券金额是否正确，方法中会计算出最终服务端价格并与前端传入的最终价格作比较
            couponChecker.finalTotalPriceisOk(orderDto.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    /**
     * 返沪订单对象需要的快照图片
     * @return
     */
    public String getSnapImg(){
        return orderSkuList.get(0).getImg();
    }

    /**
     * 返回快照标题
     * @return
     */
    public String getSnapTitle(){
        return orderSkuList.get(0).getTitle();
    }

    /**
     * 返回订单中的商品总数
     * @return
     */
    public Integer getTotalCount(){
        return orderDto.getSkuInfoDtoList().stream()
                .map(skuInfoDto -> skuInfoDto.getCount())
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * 比较前端传入的商品数量和服务端查询的数量来判断是否有下架，因为服务端不会查询出下架的商品
     * @param skuInfoDtoList
     * @param serverSkuList
     */
    private void compareMount(List<SkuInfoDto> skuInfoDtoList, List<Sku> serverSkuList){
        if (skuInfoDtoList.size() != serverSkuList.size()){
            throw new ParameterException(50002);
        }
    }

    /**
     * 判断sku是否还有库存
     * @param sku
     */
    private void isOutOfStock(Sku sku){
        if (sku.getStock() <= 0){
            throw new ParameterException(50001);
        }
    }

    /**
     * 判断购买数量是否超出库存
     * @param skuInfoDto
     * @param sku
     */
    private void isOverStock(SkuInfoDto skuInfoDto, Sku sku){
        if (skuInfoDto.getCount() > sku.getStock()){
            throw new ParameterException(50003);
        }
    }

    /**
     * 计算每一种商品的购买总价格
     * @param skuInfoDto
     * @param sku
     * @return
     */
    private BigDecimal calculateServerPrice(SkuInfoDto skuInfoDto, Sku sku){
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDto.getCount()));
    }

    /**
     * 计算不包括优惠券的情况下前后端总价格是否一致
     * @param orderTotalPrice
     * @param serverTotalPrice
     */
    private void totalPriceisOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice){
        if(orderTotalPrice.compareTo(serverTotalPrice) != 0){
            throw new ParameterException(50005);
        }
    }

}
