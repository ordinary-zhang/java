package cn.zhangyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by grace on 2018/11/28.
 */
@RestController
public class HelloController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/hello")
    public Map<String,Object> getDepartment(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from department");
        return list.get(0);
    }

    @GetMapping("/user")
    public List<Map<String, Object>> hello() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT Host,User FROM mysql.user ", new Object[]{});
        return list;
    }

}
