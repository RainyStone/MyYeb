package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * @Description: 根据用户 id 获取角色
     * @Params:
     */
    List<Role> getRoles(Integer adminId);
}
