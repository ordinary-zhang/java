package cn.zhangyu.controller;

import cn.zhangyu.bean.Department;
import cn.zhangyu.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentMapper departmentMapper;

    //查询department
    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id){
        return departmentMapper.getDepartmentById(id);
    }

    //插入数据
    @GetMapping("/dept")
    public Department insertDepartment(@RequestParam("departmentName") String departmentName){
        departmentMapper.insertDept(departmentName);
        Department department = departmentMapper.getDepartmentByName(departmentName);
        return department;
    }
}
