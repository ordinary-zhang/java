package cn.zhangyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(value = "cn.zhangyu.mapper")
@SpringBootApplication
public class SpringbootJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJdbcApplication.class, args);
	}
}
