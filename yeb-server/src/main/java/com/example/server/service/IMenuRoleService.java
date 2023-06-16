package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.MenuRole;
import com.example.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * @Author: example
     * @Description: 更新角色菜单
     * @Params: 
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
