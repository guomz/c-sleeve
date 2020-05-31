package com.guomz.csleeve.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guomz.csleeve.entity.WxAuthEntity;
import com.guomz.csleeve.exception.http.ParameterException;
import com.guomz.csleeve.model.User;
import com.guomz.csleeve.repository.UserRepository;
import com.guomz.csleeve.utils.JsonConverterVer2;
import com.guomz.csleeve.utils.JwtCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

/**
 * 处理微信登陆或注册的业务
 */
@Service
public class WxAuthenticationServiceImpl {

    @Autowired
    private UserRepository userRepository;
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.appsecret}")
    private String appSecret;
    @Value("${wx.getCodeUrl}")
    private String getCodeUrl;

    /**
     * 向微信服务器验证并返回jwt令牌
     * @param code
     * @return
     */
    public String codeToSession(String code){
        this.getCodeUrl = MessageFormat.format(this.getCodeUrl, this.appId, this.appSecret, code);
        RestTemplate restTemplate = new RestTemplate();
        String wxAuthStr = restTemplate.getForObject(this.getCodeUrl, String.class);
        System.out.println(wxAuthStr);
        WxAuthEntity wxAuthEntity = new WxAuthEntity();
        wxAuthEntity = JsonConverterVer2.jsonToObejct(wxAuthStr, new TypeReference<WxAuthEntity>() {});
        String token = registerUser(wxAuthEntity);
        return token;
    }

    /**
     * 判断登陆or注册，并返回令牌
     * @param wxAuthEntity
     * @return
     */
    private String registerUser(WxAuthEntity wxAuthEntity){
        //先判断openid
        String openid = wxAuthEntity.getOpenid();
        if(StringUtils.isEmpty(openid)){
            throw new ParameterException(20004);
        }

        User user = userRepository.findUserByOpenid(openid);
        //第一次登陆即为注册
        if(user == null){
            user = User.builder().openid(openid).build();
            userRepository.save(user);
            //调用jwt令牌返回
            String token = JwtCodeUtil.generateJwtCode(user.getId());
            return token;
        }
        //调用令牌
        String token = JwtCodeUtil.generateJwtCode(user.getId());
        return token;
    }
}
