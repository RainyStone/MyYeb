package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
public interface MenuMapper extends BaseMapper<Menu> {


    /**
     * @Description: 根据用户 id 查询菜单列表
     * @Params: 
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * @Description: 根据角色获取菜单列表
     * @Params:
     */
    List<Menu> getMenusWithRole();

    List<Menu> getAllMenus();
}
