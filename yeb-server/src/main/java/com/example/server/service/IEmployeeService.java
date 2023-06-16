package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Employee;
import com.example.server.pojo.RespBean;
import com.example.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * @Author: example
     * @Description: 获取所有员工（分页）
     * @Params: 
     */
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * @Author: example
     * @Description: 获取最大工号
     */
    RespBean maxWorkID();

    /**
     * @Author: example
     * @Description: 添加员工
     * @param employee
     */
    RespBean addEmp(Employee employee);

    /**
     * @Author: example
     * @Description: 查询员工
     * @param id
     */
    List<Employee> getEmployee(Integer id);

    /**
     * @Description: 获取所有员工账套
     * @param currentPage
     * @param size
     */
    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
