package com.guomz.csleeve.core;

import com.guomz.csleeve.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放取出当前线程的用户信息与权限
 */
public class LocalUser {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static void setUser(User user, int scope){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("user", user);
        userMap.put("scope", scope);
        threadLocal.set(userMap);
    }

    public static User getUser(){
        Map<String, Object> userMap = threadLocal.get();
        User user = (User) userMap.get("user");
        return user;
    }

    public static int getScope(){
        Map<String, Object> userMap = threadLocal.get();
        int scope = (int) userMap.get("scope");
        return scope;
    }

    public static void clearUser(){
        threadLocal.remove();
    }
}
