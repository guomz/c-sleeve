package com.guomz.csleeve.api.v1;

import com.guomz.csleeve.model.Theme;
import com.guomz.csleeve.service.ThemeService;
import com.guomz.csleeve.vo.ThemePureVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/theme")
@Validated
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    /**
     * 按照主题名称返回主题
     * @param names
     * @return
     */
    @GetMapping("/by/names")
    public List<ThemePureVo> getThemesByNames(@RequestParam("names") String names){
        List<String> nameList = Arrays.asList(names.split(","));
        List<Theme> themeList = themeService.getByNames(nameList);
        List<ThemePureVo> themePureVoList = themeList.stream().map(theme -> {
            ThemePureVo themePureVo = new ThemePureVo();
            BeanUtils.copyProperties(theme, themePureVo);
            return themePureVo;
        }).collect(Collectors.toList());
        return themePureVoList;
    }
}
