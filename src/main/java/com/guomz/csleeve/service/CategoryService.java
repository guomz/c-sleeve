package com.guomz.csleeve.service;

import com.guomz.csleeve.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    public Map<String, List<Category>> getAllCategories();
}
