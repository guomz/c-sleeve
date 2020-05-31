package com.guomz.csleeve.core.configuration;

import com.guomz.csleeve.core.interceptors.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    //需要将被注册的拦截器配置到springboot容器中，否则在拦截器中注入的类会空指针
    @Bean
    public PermissionInterceptor getPermissionInteceptpr(){
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPermissionInteceptpr());
    }
}
