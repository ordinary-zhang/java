package cn.zhangyu.controller;

import cn.zhangyu.bean.Employee;
import cn.zhangyu.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by grace on 2018/11/28.
 */
@RestController
public class EmployeeController {

    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping("/emp/{id}")
    public Employee getEmpById(@PathVariable("id") Integer id){
        return employeeMapper.getEmpById(id);
    }

    @GetMapping("/emp")
    public Employee insertEmp(Employee employee){
        employeeMapper.insertEmp(employee);
        return employee;
    }
}
