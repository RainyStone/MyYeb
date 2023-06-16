package com.example.server.config.security.component;

import com.example.server.pojo.Menu;
import com.example.server.pojo.Role;
import com.example.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @Description: 权限控制，根据角色身份判断用户访问的url是否符合权限控制
 * 即，用户访问某url，由于url下各菜单项有角色要求，判断该用户拥有的角色身份是否可以访问各菜单项
 * FilterInvocationSecurityMetadataSource 接口是 Spring Security 中的核心接口之一，用于获取指定请求所需的访问控制元数据，包括访问所需的权限、角色等信息
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private IMenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求的 url
        String requestUrl = ((FilterInvocation)object).getRequestUrl();
        List<Menu> menus = menuService.getMenusWithRole();
        // 判断请求 url 与菜单角色是否匹配
        for (Menu menu : menus) {
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        // 没匹配的 url 默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
