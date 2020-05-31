package com.guomz.csleeve.service;

import com.guomz.csleeve.model.GridCategory;
import com.guomz.csleeve.repository.GridCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GridCategoryImpl implements GridCategoryService {

    @Autowired
    private GridCategoryRepository gridCategoryRepository;

    /**
     * 返回全部宫格类别
     * @return
     */
    @Override
    public List<GridCategory> getAllGridCategories() {
        return gridCategoryRepository.findAll();
    }
}
