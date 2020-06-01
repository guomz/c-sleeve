package com.guomz.csleeve.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成jwt令牌的工具
 */
@Component
public class JwtCodeUtil {

    private static String salt;
    private static int expiredTime;
    private static int defaultScope;

    //用set方法可以为静态属性使用value赋值
    @Value("${jwt.salt}")
    public void setSalt(String s){
        salt = s;
    }

    @Value("${jwt.expiredTime}")
    public void setExpiredTime(int time){
        expiredTime = time;
    }
    //用户默认等级
    @Value("${jwt.defaultScope}")
    public void setDefaultScope(int scope){
        defaultScope = scope;
    }

    /**
     * 生成jwt的code，可指定用户级别
     * @param userId
     * @param scope
     * @return
     */
    public static String generateJwtCode(Long userId, int scope){
        Map<String, Date> timeMap = calcuExpiredTime(expiredTime);
        String token = JWT.create().withClaim("userId", userId)
                .withClaim("scope", scope)
                .withIssuedAt(timeMap.get("now"))
                .withExpiresAt(timeMap.get("expired"))
                .sign(Algorithm.HMAC256(salt));
        return token;
    }

    /**
     * 不指定用户级别，即使用默认级别
     * @param userId
     * @return
     */
    public static String generateJwtCode(Long userId){
        Map<String, Date> timeMap = calcuExpiredTime(expiredTime);
        String token = JWT.create().withClaim("userId", userId)
                .withClaim("scope", defaultScope)
                .withIssuedAt(timeMap.get("now"))
                .withExpiresAt(timeMap.get("expired"))
                .sign(Algorithm.HMAC256(salt));
        return token;
    }

    /**
     * 解析传入的jwt令牌
     * @param jwtCode
     * @return
     */
    public static Map<String, Claim> getClaims(String jwtCode){
        DecodedJWT decodedJWT;
        Algorithm algorithm = Algorithm.HMAC256(salt);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            decodedJWT = jwtVerifier.verify(jwtCode);
        } catch (JWTVerificationException e) {
            return null;
        }
        return decodedJWT.getClaims();
    }

    /**
     * 验证令牌是否有效
     * @param token
     * @return
     */
    public static boolean verifyJwtToken(String token){
        DecodedJWT decodedJWT;
        Algorithm algorithm = Algorithm.HMAC256(salt);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    /**
     * 计算到期时间并返回当前时间于到期时间
     * @param expiredTime
     * @return
     */
    private static Map<String, Date> calcuExpiredTime(int expiredTime){
        Map<String, Date> timeMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.add(Calendar.SECOND, expiredTime);
        Date expired = calendar.getTime();
        timeMap.put("now", now);
        timeMap.put("expired", expired);
        System.out.println(timeMap);
        return timeMap;
    }
}
