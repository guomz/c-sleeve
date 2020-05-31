package com.guomz.csleeve.service;

import com.guomz.csleeve.model.Theme;
import com.guomz.csleeve.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService{

    @Autowired
    private ThemeRepository themeRepository;

    /**
     * 按照名称查找主题
     * @param nameList
     * @return
     */
    public List<Theme> getByNames(List<String> nameList){
        List<Theme> themeList = themeRepository.findByNameIn(nameList);
        return themeList;
    }

    public Theme getByName(String name){
        return themeRepository.findThemeByName(name);
    }
}
