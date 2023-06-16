package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Department;
import com.example.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * @Description: 获取所有部门
     * @Params: 
     */
    List<Department> getAllDepartments();

    /**
     * @Description: 添加部门
     * @Params: 
     */
    RespBean addDep(Department department);

    /**
     * @Description: 删除部门
     * @Params: 
     */
    RespBean delDep(Integer id);
}
