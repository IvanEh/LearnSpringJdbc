package com.gmail.at.ivanehreshi;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@ImportResource("classpath:/context.xml") // xml file for old style configuration
public class AppConfig implements InitializingBean {

    // It's is a must to set up data source bean(or beans)
    @Bean
    public DataSource dataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/social_network");
        ds.setUser("ivaneh");
        ds.setPassword("password");
        return ds;
    }

    // Now we will use NamedParameterJdbcTempalte
    @Bean
    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AppConfig initialized");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserDao userDao = (UserDao) ctx.getBean("userDao");
        userDao.batchCreateRandom(11);
    }
}
