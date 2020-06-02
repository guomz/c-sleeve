package com.guomz.csleeve.api.v1;

import com.guomz.csleeve.dto.TokenGetDto;
import com.guomz.csleeve.dto.TokenVerifyDto;
import com.guomz.csleeve.exception.http.ForbiddenException;
import com.guomz.csleeve.service.WxAuthenticationServiceImpl;
import com.guomz.csleeve.utils.JwtCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/token")
public class TokenController {

    @Autowired
    private WxAuthenticationServiceImpl wxAuthenticationService;

    /**
     * 获取token接口
     * @param tokenGetDto
     * @return
     */
    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Validated TokenGetDto tokenGetDto){

        if(tokenGetDto == null){
            throw new ForbiddenException(10003);
        }
        Map<String, String> tokenResult = new HashMap<>();

        switch (tokenGetDto.getType()){
            case USER_WX:
                String token = wxAuthenticationService.codeToSession(tokenGetDto.getAccount());
                tokenResult.put("token", token);
                break;
            case USER_EMAIL:
                break;
            default:
                throw new ForbiddenException(10003);
        }
        return tokenResult;
    }

    /**
     * 用于前端验证token是否有效，小程序会将token放入缓存并在适当的时候调用该方法校验
     * @return
     */
    @PostMapping("/verify")
    public Map<String, Boolean> verifyToken(@RequestBody TokenVerifyDto tokenVerifyDto){
        Map<String, Boolean> verifyResult = new HashMap<>();
        boolean result = JwtCodeUtil.verifyJwtToken(tokenVerifyDto.getToken());
        verifyResult.put("is_valid", result);
        return verifyResult;
    }
}
