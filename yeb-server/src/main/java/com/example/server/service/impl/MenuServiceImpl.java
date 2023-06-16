package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.MenuMapper;
import com.example.server.pojo.Menu;
import com.example.server.service.IMenuService;
import com.example.server.utils.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {


    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * @Author: example
     * @Description: 根据用户id 查询菜单列表
     * @Params:
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        Integer adminId = AdminUtils.getCurrentAdmin().getId();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        //从redis中获取菜单数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_"+adminId);
        //如果为空，去数据库获取
        if(CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(adminId);
            //将数据设置到redis中
            valueOperations.set("menu_"+adminId,menus);
        }

        return menus;
    }


    /**
     * @Description: 根据角色获取菜单列表
     * @Params:
     */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    /**
     * @Author: example
     * @Description: 查询所有菜单
     * @Params:
     */
    @Override
    public List<Menu> getAllMenus(){
        return menuMapper.getAllMenus();
    }
}
