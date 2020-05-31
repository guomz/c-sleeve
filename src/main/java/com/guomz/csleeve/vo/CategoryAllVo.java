package com.guomz.csleeve.vo;

import com.guomz.csleeve.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 由于前端需要区分分类级别，故再次封装一层
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryAllVo {

    private List<CategoryPureVo> rootCategory;
    private List<CategoryPureVo> subCategory;

    public CategoryAllVo(Map<String, List<Category>> categoryMap){
        List<Category> rootList = categoryMap.get("root");
        this.rootCategory = rootList.stream().map(CategoryPureVo::new).collect(Collectors.toList());
        List<Category> subList = categoryMap.get("sub");
        this.subCategory = subList.stream().map(CategoryPureVo::new).collect(Collectors.toList());
    }
}
