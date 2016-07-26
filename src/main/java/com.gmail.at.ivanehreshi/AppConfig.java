package com.gmail.at.ivanehreshi;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@ImportResource("classpath:/context.xml")
public class AppConfig implements InitializingBean {

    @Bean
    public DataSource dataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://localhost:3306/social_network");
        ds.setUser("ivaneh");
        ds.setPassword("password");

        return ds;
    }

    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AppConfig initialized");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserDao userDao = (UserDao) ctx.getBean("userDao");
        User user = new User("Vasya Pupkin", 14);
        userDao.create(user);
    }
}
