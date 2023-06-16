package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.MenuRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2022-05-11
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * @Description: 批量更新
     * @Params:
     */
    Integer insertRecord(Integer rid, Integer[] mids);
}
