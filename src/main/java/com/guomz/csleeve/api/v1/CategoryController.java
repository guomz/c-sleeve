package com.guomz.csleeve.api.v1;

import com.guomz.csleeve.model.GridCategory;
import com.guomz.csleeve.service.CategoryService;
import com.guomz.csleeve.service.GridCategoryService;
import com.guomz.csleeve.vo.CategoryAllVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GridCategoryService gridCategoryService;

    /**
     * 分类返回全部一二级类别
     * @return
     */
    @GetMapping("/all")
    public CategoryAllVo getAllCategories(){
        return new CategoryAllVo(categoryService.getAllCategories());
    }

    /**
     * 返回全部宫格类别
     * @return
     */
    @GetMapping("/grid/all")
    public List<GridCategory> getAllGridCategories(){
        return gridCategoryService.getAllGridCategories();
    }
}
