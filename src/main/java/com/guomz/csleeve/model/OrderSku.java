package com.guomz.csleeve.model;

import com.guomz.csleeve.dto.SkuInfoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
/**
 * order实体中的snapitem的序列化对象
 */
public class OrderSku {

    private Long id;
    private Long spuId;
    private BigDecimal finalTotalPrice;
    private BigDecimal singlePrice;
    private List<String> specValueList;
    private Integer count;
    private String img;
    private String title;

    public OrderSku(SkuInfoDto skuInfoDto, Sku sku){
        this.id = sku.getId();
        this.spuId = sku.getSpuId();
        this.finalTotalPrice = sku.getActualPrice().multiply(new BigDecimal(skuInfoDto.getCount()));
        this.singlePrice = sku.getActualPrice();
        this.specValueList = sku.getSpecValue();
        this.count = skuInfoDto.getCount();
        this.img = sku.getImg();
        this.title = sku.getTitle();
    }
}
