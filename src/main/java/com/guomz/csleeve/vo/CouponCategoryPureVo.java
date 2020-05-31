package com.guomz.csleeve.vo;

import com.guomz.csleeve.model.Category;
import com.guomz.csleeve.model.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CouponCategoryPureVo extends CouponPureVo {

    private List<CategoryPureVo> categoryList;

    public CouponCategoryPureVo(Coupon coupon){
        super(coupon);
        List<Category> categoryList = coupon.getCategoryList();
        this.categoryList = new ArrayList<>();
        categoryList.forEach(category -> {
            CategoryPureVo categoryPureVo = new CategoryPureVo(category);
            this.categoryList.add(categoryPureVo);
        });
    }

    public static List<CouponCategoryPureVo> getListWithCategory(List<Coupon> couponList){
        List<CouponCategoryPureVo> couponCategoryPureVoList = couponList.stream().map(coupon -> {
            CouponCategoryPureVo couponCategoryPureVo = new CouponCategoryPureVo(coupon);
            return couponCategoryPureVo;
        }).collect(Collectors.toList());
        return couponCategoryPureVoList;
    }
}
