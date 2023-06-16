package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.pojo.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
public interface EmployeeMapper extends BaseMapper<Employee> {




    /**
     * @Description: 获取所有员工（分页）
     * @param page
     * @param employee
     * @param beginDateScope
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page, Employee employee, LocalDate[] beginDateScope);


    List<Employee> getEmployee(Integer id);

    /**
     * @Description: 获取所有员工账套
     * @param page
     */
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
