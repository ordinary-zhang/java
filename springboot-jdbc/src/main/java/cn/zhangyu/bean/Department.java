package cn.zhangyu.bean;

import java.io.Serializable;

/**
 * Created by grace on 2018/11/28.
 */
public class Department implements Serializable{

    private Integer id;

    private String departmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
