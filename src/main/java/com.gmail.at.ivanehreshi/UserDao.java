package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Dao can be marked either with @Repository or @Component to
// be managed by Spring
@Repository
public class UserDao implements Dao<User, Integer> {
    @Autowired
    NamedParameterJdbcTemplate jdbc;

    @Override
    public Integer create(User user) {
        return createWithBeanPropertySqlParameterSource(user);
    }

    private Integer createWithBeanPropertySqlParameterSource(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        jdbc.update("INSERT INTO user (name, age) VALUES(:name, :age)", parameterSource, keyHolder);
        return Optional.ofNullable(keyHolder.getKey()).orElse(-1).intValue();
    }


    // with getJdbcOperations() we retrieve the JdbcTemplate object so we
    // can use its functions
    @Override
    public void update(User user) {
        jdbc.getJdbcOperations().update("UPDATE user SET name=? age=? WHERE id=?", user.name
                                                        , user.age
                                                        , user.id );
    }

    @Override
    public User read(Integer id) {
        try {
            return jdbc.getJdbcOperations().query("SELECT name, age FROM user WHERE id = ? LIMIT 1",
                    (rs, rowNum) -> new User(id, rs.getString("name"), rs.getInt("age")),
                    id).stream()
                       .findFirst()
                       .orElseGet(() -> null);
        }catch (EmptyResultDataAccessException e) {
            return new User("Empty", 17);
        }
    }

    @Override
    public void delete(Integer id) {
        jdbc.getJdbcOperations().update("DELETE FROM user WHERE id=?", id);
    }

    public NamedParameterJdbcTemplate getJdbc() {
        return jdbc;
    }

    public void setJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
