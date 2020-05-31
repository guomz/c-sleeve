package com.guomz.csleeve.service;

import com.guomz.csleeve.model.Category;
import com.guomz.csleeve.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 获取全部类别分出级别放入map返回
     * @return
     */
    public Map<String, List<Category>> getAllCategories(){
        List<Category> rootCategory = categoryRepository.findAllByIsRoot(true);
        List<Category> subCategory = categoryRepository.findAllByIsRoot(false);
        Map<String, List<Category>> categoryMap = new HashMap<>();
        categoryMap.put("root", rootCategory);
        categoryMap.put("sub", subCategory);
        return categoryMap;
    }

}
