package com.guomz.csleeve.api.v1;

import com.guomz.csleeve.exception.http.NotFoundException;
import com.guomz.csleeve.model.Banner;
import com.guomz.csleeve.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1/banner")
@Validated
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping("/test")
    public String testMethod(){
        throw new NotFoundException(10001);
        //return "hello";
    }

    @RequestMapping("/test2")
    public String testMethod2(){
        return "hello";
    }

    @GetMapping("/id/{id}")
    public Banner getBannerById(@PathVariable("id") Long id){
        Banner banner = bannerService.getById(id);
        if(banner == null){
            throw new NotFoundException(30005);
        }
        return banner;
    }

    @GetMapping("/name/{name}")
    public Banner getBannerByName(@PathVariable("name")@NotBlank String name){
        Banner banner = bannerService.getByName(name);
        if(banner == null){
            throw new NotFoundException(30005);
        }
        return banner;
    }
}
