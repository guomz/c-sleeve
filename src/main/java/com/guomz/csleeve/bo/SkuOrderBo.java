package com.guomz.csleeve.bo;

import com.guomz.csleeve.dto.SkuInfoDto;
import com.guomz.csleeve.model.Sku;
import lombok.Getter;
import lombok.Setter;
/**
 * 供业务层处理的sku信息
 */
import java.math.BigDecimal;
@Getter
@Setter
public class SkuOrderBo {

    private BigDecimal actualPrice;
    private Integer count;
    private Long categoryId;

    public SkuOrderBo(Sku sku, SkuInfoDto skuInfoDto){
        this.actualPrice = sku.getActualPrice();
        this.count = skuInfoDto.getCount();
        this.categoryId = sku.getCategoryId();
    }
}
