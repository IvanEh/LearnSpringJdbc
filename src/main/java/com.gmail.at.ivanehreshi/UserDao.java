package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

// Dao can be marked either with @Repository or @Component to
// be managed by Spring
@Repository
public class UserDao implements Dao<User, Integer> {
    // JdbcTemplate is the core class for easily using jdbc
    // There are also such useful classes as: NamedParameterJdbcTemplate, SimpleJdbcInsert, SimpleJdbcCall
    @Autowired
    JdbcTemplate jdbc;

    // Standard Jdbc question mark substitution
    @Override
    public Integer create(User user) {
        jdbc.update("INSERT INTO user (name, age) VALUES(?, ?)", user.name, user.age);
        return null;
    }

    @Override
    public void update(User user) {
        jdbc.update("UPDATE user SET name=? age=? WHERE id=?", user.name
                                                        , user.age
                                                        , user.id );
    }

    // The lambda is implementing ResultSetExtractor interface
    // in Spring Jdbc many manipulation are done via functional interfaces
    // EmptyResultDataAccessException is thrown when there is no data 
    @Override
    public User read(Integer id) {
        try {
            return jdbc.query("SELECT name, age FROM user WHERE id=?",
                    (ResultSet rs) -> new User(id, rs.getString("name"), rs.getInt("age")), id);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        jdbc.update("DELETE FROM user WHERE id=?", id);
    }

    public JdbcTemplate getJdbc() {
        return jdbc;
    }

    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
