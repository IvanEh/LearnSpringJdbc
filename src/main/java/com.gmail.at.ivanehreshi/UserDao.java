package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;

// Dao can be marked either with @Repository or @Component to
// be managed by Spring
@Repository
public class UserDao implements Dao<User, Integer> {

    @Autowired
    DataSource dataSource;

    SimpleJdbcInsert jdbcInsert;
    NamedParameterJdbcTemplate jdbc;

    @Override
    public Integer create(User user) {
        return jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(user))
                         .intValue();
    }

    @Override
    public void update(User user) {
        throw new NotImplementedException();
    }

    @Override
    public User read(Integer integer) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Integer integer) {
        throw new NotImplementedException();
    }

    // generated key columns should be specified to be able to retrieve keys
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("user")
                            .usingGeneratedKeyColumns("id");
    }
}
