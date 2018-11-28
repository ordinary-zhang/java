package cn.zhangyu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static sun.misc.Version.println;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJdbcApplicationTests {

	@Autowired
	DataSource dataSource;

	@Test
	public void contextLoads() throws SQLException {
	Connection connection = dataSource.getConnection();
	System.out.println("connection::"+connection);
	connection.close();

	}

}
