package com.guomz.csleeve.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThemePureVo {

    private Long id;
    private String title;
    private String description;
    private String name;
    //用于小程序端所使用的模板标识
    private String tplName;
    private String entranceImg;
    private String extend;
    private String internalTopImg;
    private String titleImg;
    private Boolean online;
}
