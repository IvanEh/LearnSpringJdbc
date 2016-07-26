package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
public class UserDao implements Dao<User, Integer> {
    @Autowired
    JdbcTemplate jdbc;

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
