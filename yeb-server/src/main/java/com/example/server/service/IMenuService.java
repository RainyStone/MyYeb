package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IMenuService extends IService<Menu> {

    /**
     * @Author: example
     * @Description: 根据用户 id 查询菜单列表
     * @Params: 
     */
    List<Menu> getMenuByAdminId();

    /**
     * @Description: 根据角色获取菜单列表
     * @Params: 
     */
    List<Menu> getMenusWithRole();

    /**
     * @Description: 查询所有菜单
     * @Params: 
     */
    List<Menu> getAllMenus();
}
