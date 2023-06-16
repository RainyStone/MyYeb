package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * @Description: getAllDepartments
     * @Params: 
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * @Description: 添加部门
     * @Params: 
     */
    void addDep(Department dep);

    void delDep(Department dep);
}
