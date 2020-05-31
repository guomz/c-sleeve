package com.guomz.csleeve.vo;

import com.guomz.csleeve.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 用于简化类别中的数据，避免由于类别的一对多关系造成的循环序列化
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryPureVo {

    private Long id;
    private String name;
    private String description;
    private Boolean isRoot;
    private String img;
    private Long parentId;
    private Long index;

    public CategoryPureVo(Category category){
        BeanUtils.copyProperties(category, this);
    }
}
