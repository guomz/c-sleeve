package com.guomz.csleeve.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerHome {

    /**
     * 配置swagger页面路由，当前配置访问localhost 8080首页默认为swagger
     * @return
     */
    @GetMapping(value = {"/", "/swagger"})
    public String home(){
        return "redirect:/swagger-ui.html";
    }
}
