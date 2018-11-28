package cn.zhangyu.mapper;

import cn.zhangyu.bean.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 指定操作数据库表department
 */
//@Mapper
@Repository
public interface DepartmentMapper {

    @Select("select * from department where id=#{id}")
    public Department getDepartmentById(Integer id);

    @Select("select * from department where department_name=#{departmentName}")
    public Department getDepartmentByName(String name);

    @Delete("delete from department where id=#{id}")
    public int deleteDepartment(Integer id);

    //@Options 确认使用自增主键 、返回主键id 不使用@Options返回的id=null
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into department(departmentName) values(#{departmentName})")
    public int insertDept(String departmentName);

    @Update("update department set department_name=#{departmentName} where id=#{id}")
    public int updateDept(Department department);
}

