package cn.zhangyu.mapper;


import cn.zhangyu.bean.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

//@Mapper或者@MapperScan将接口扫描装配到容器中
@Mapper
@Repository
public interface EmployeeMapper {

    //@Select("select * from employee where id=#{id}")
    public Employee getEmpById(Integer id);

   // @Select("insert into employee(lastName) values(#{lastName})")
    public void insertEmp(Employee employee);
}
