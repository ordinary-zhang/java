package cn.zhangyu.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 适用于驼峰命名法
 *  再mapper中编写的@Insert("insert into department(departmentName) values(#{departmentName})")
 *  可以写成驼峰式：@Insert("insert into department(department_name) values(#{departmentName})")
 */

@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setMapUnderscoreToCamelCase(true);//设置驼峰命名规则
        };
    }
}
