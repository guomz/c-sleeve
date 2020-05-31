package com.guomz.csleeve.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.guomz.csleeve.core.LocalUser;
import com.guomz.csleeve.exception.http.ForbiddenException;
import com.guomz.csleeve.exception.http.UnAuthenticatedException;
import com.guomz.csleeve.model.User;
import com.guomz.csleeve.service.UserServiceImpl;
import com.guomz.csleeve.utils.JwtCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取注解信息
        ScopeLevel scopeLevel = getScopeLevel(handler);
        if(scopeLevel == null){
            //没有该注解即为公共方法，放行
            return true;
        }
        //获取请求携带的令牌并取出scope等级
        String jwtCode = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwtCode)){
            throw new UnAuthenticatedException(10004);
        }
        //检查令牌是否符合规范 ，即以Bearer开头
        if(!jwtCode.startsWith("Bearer")){
            throw new UnAuthenticatedException(10004);
        }
        //取出真正的token部分
        String token = jwtCode.split(" ")[1];
        Map<String, Claim> claimMap = JwtCodeUtil.getClaims(token);
        if(claimMap == null){
            throw new UnAuthenticatedException(10004);
        }
        //验证权限等级
        boolean isPermitted = hasPermission(scopeLevel, claimMap);
        if(!isPermitted){
            throw new ForbiddenException(10005);
        }
        //将用户放入线程存储
        serUserToThreadLocal(claimMap);
        return isPermitted;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //请求结束后清除存储的信息
        LocalUser.clearUser();
    }

    /**
     * 验证是否有权限
     * @param scopeLevel
     * @param claimMap
     * @return
     */
    private boolean hasPermission(ScopeLevel scopeLevel, Map<String, Claim> claimMap){
        int level = scopeLevel.level();
        int scope = claimMap.get("scope").asInt();
        if(scope < level){
            return false;
        }
        return true;
    }

    /**
     * 将当前登陆的用户信息放入线程存储
     * @param claimMap
     */
    private void serUserToThreadLocal(Map<String, Claim> claimMap){
        Long userId = claimMap.get("userId").asLong();
        int scope = claimMap.get("scope").asInt();
        User user = userService.getUserById(userId);
        LocalUser.setUser(user, scope);
    }

    /**
     * 获取方法上的权限注解
     * @param handler
     * @return
     */
    private ScopeLevel getScopeLevel(Object handler){
        //判断handler是否是HandlerMethod的直接或间接子类实现类，或者直接是实例
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            return scopeLevel;
        }
        return null;
    }
}
