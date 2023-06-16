package com.example.server.utils;

import com.example.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminUtils {
    
    /**
     * @Description: 获取当前登录操作员
     * @Params: 
     */
    public static Admin getCurrentAdmin(){
        return ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
