package com.guomz.csleeve.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guomz.csleeve.utils.JsonConverterVer2;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Sku extends BaseEntity{
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    //指定特定的序列化类
    //@Convert(converter = JsonConverter.class)
    //private List<Spec> specs;
    private String specs;
    private String code;
    private int stock;
    private Long categoryId;
    private Long rootCategoryId;

    //返回当前sku的价格，有折扣价就返回折扣价
    public BigDecimal getActualPrice(){
        return discountPrice != null ? discountPrice:price;
    }

    public List<Spec> getSpecs() {
        return JsonConverterVer2.jsonToObejct(this.specs, new TypeReference<List<Spec>>() {});
    }

    public void setSpecs(List<Spec> specList) {
        this.specs = JsonConverterVer2.objectToJson(specList);
    }

    /**
     * 返回当前商品的规格值数组
     * @return
     */
    public List<String> getSpecValue(){
        return this.getSpecs().stream().map(spec -> spec.getValue()).collect(Collectors.toList());
    }
}
