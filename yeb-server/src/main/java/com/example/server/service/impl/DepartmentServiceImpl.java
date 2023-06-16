package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.DepartmentMapper;
import com.example.server.pojo.Department;
import com.example.server.pojo.RespBean;
import com.example.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * @Description: 获取所有部门
     * @Params: 
     */
    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    /**
     * @Description: 添加部门
     * @Params:
     */
    @Override
    public RespBean addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
        if(1 == department.getResult()) {
            return RespBean.success("添加成功",department);
        }
        return RespBean.error("添加失败");
    }

    /**
     * @Description: 删除部门
     * @Params: 
     */
    @Override
    public RespBean delDep(Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentMapper.delDep(dep);
        if(-2 == dep.getResult()){
            return RespBean.error("该部门下还有子部门，删除失败！");
        }

        if(-1 == dep.getResult()){
            return RespBean.error("该部门下还有员工，删除失败！");
        }

        if(1 == dep.getResult()){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
