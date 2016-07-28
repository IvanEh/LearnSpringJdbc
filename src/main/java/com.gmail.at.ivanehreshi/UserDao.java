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
    // JdbcTemplate is the core class for easily using jdbc
    // There are also such useful classes as: NamedParameterJdbcTemplate, SimpleJdbcInsert, SimpleJdbcCall
    @Autowired
    NamedParameterJdbcTemplate jdbc;

    // Three usages of NamedParameterJdbcTemplate. Uncomment to test
    // Parameters introduced by ':' symbol
    @Override
    public Integer create(User user) {
        return createWithJavaMap(user); // This actually always return null
//        return createWithKeyHolderAndMapParameterSource(user);
//        return createWithBeanPropertySqlParameterSource(user);
    }

    // There is a Map<String, Object> used for substituting parameters
    // Limitation: cannot pass KeyHolder object to retrieve row ids
    private Integer createWithJavaMap(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("age", user.age);
        params.put("name", user.name);

        jdbc.update("INSERT INTO user (name, age) VALUES(:name, :age)", params);
        return null;
    }

    // First example of SqlParameterSource usage
    //
    // From docs: Interface that defines common functionality for objects that can offer
    // parameter values for named SQL parameters, serving as argument for
    // NamedParameterJdbcTemplate operations.
    //
    // Generally it is the previous solution but with the usage of more powerful
    // SqlParameterSource. The particular impl MapSqlParameterSource uses a Java
    // Map<String, Object> map to provide parameter values
    //
    // Method signatures that uses SqlParameterSource allows to pass KeyHolder object
    // which will store row ids once the query is executed
    private Integer createWithKeyHolderAndMapParameterSource(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("age", user.age);
        params.put("name", user.name);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource(params);
        jdbc.update("INSERT INTO user (name, age) VALUES(:name, :age)", parameterSource, keyHolder);
        return Optional.ofNullable(keyHolder.getKey()).orElse(-1).intValue();
    }

    // BeanPropertySqlParameterSource a particularly useful impl of SqlParameterSource
    // that automatically maps bean properties to parameter properties
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

    // The lambda is implementing RowMapper interface
    // in Spring Jdbc many manipulation are done via functional interfaces
    // EmptyResultDataAccessException is thrown when there is no data
    // More on this will be later
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

    // nothing changed
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
