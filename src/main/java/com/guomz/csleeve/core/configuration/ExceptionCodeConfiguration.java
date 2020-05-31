package com.guomz.csleeve.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于加载指定配置文件中的错误码，实体中对应属性名称保持一致，需要有get set方法
 */
@Component
@PropertySource(value = "classpath:config/exception-config.properties", encoding = "utf-8")
@ConfigurationProperties(prefix = "guo")
public class ExceptionCodeConfiguration {

    private Map<Integer, String> codes = new HashMap<>();

    public String getMessage(int code){
        return codes.get(code);
    }

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }
}
