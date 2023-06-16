package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.AdminRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer addAdminRole(Integer adminId, Integer[] rids);
}
