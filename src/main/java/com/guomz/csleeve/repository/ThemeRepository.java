package com.guomz.csleeve.repository;

import com.guomz.csleeve.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    List<Theme> findByNameIn(List<String> nameList);

    Theme findThemeByName(String name);
}
