package com.guomz.csleeve.service;

import com.guomz.csleeve.model.Theme;

import java.util.List;

public interface ThemeService {
    public List<Theme> getByNames(List<String> nameList);

    public Theme getByName(String name);
}
