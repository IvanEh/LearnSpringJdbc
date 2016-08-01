package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.IntStream;

// Dao can be marked either with @Repository or @Component to
// be managed by Spring
@Repository
public class UserDao implements Dao<User, Integer> {
    @Autowired
    NamedParameterJdbcTemplate jdbc;

    public void batchCreateRandom(int n) {
//        batchCreateRandom_BatchPreparedStatementsSetter(n);
//        batchCreateRandom_InterruptedBatchPreparedStatementSetter(n);
//        batchCreateRandom_Array_UnnamedParameters(n);
//        batchCreateRandom_Array_NamedParameters(n);
        batchCreateRandom_MultipleBatches(n);
    }

    void batchCreateRandom_BatchPreparedStatementsSetter(int n) {
        jdbc.getJdbcOperations().batchUpdate("INSERT INTO user(name, age) VALUES(?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        User user = User.randomUser();
                        ps.setString(1, user.getName());
                        ps.setInt(2, user.getAge());
                    }

                    @Override
                    public int getBatchSize() {
                        return n;
                    }
                });
    }

    void batchCreateRandom_InterruptedBatchPreparedStatementSetter(int n) {
        jdbc.getJdbcOperations().batchUpdate("INSERT INTO user(name, age) VALUES(?, ?)",
                new InterruptibleBatchPreparedStatementSetter() {
                    @Override
                    public boolean isBatchExhausted(int i) {
                        return i > n;
                    }

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        User user = User.randomUser();
                        ps.setString(1, user.getName());
                        ps.setInt(2, user.getAge());
                    }

                    @Override
                    public int getBatchSize() {
                        return 10000001;
                    }
                });
    }

    void batchCreateRandom_Array_NamedParameters(int n) {
        User[] users = new User[n];
        IntStream.range(0, n)
                 .forEach((i) -> users[i] = User.randomUser());

        SqlParameterSource[] paramSources = SqlParameterSourceUtils.createBatch(users);
        jdbc.batchUpdate("INSERT INTO user(name, age) VALUES (:name, :age)", paramSources);
    }

    void batchCreateRandom_Array_UnnamedParameters(int n) {
        List<Object[]> args = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            User user = User.randomUser();
            args.add(new Object[] {user.getName(), user.getAge()});
        }

        jdbc.getJdbcOperations().batchUpdate("INSERT INTO user(name, age) VALUES(?, ?)",
                args);
    }

    void batchCreateRandom_MultipleBatches(int n) {
        List<User> users = new ArrayList<>(n);
        IntStream.range(0, n)
                 .forEach(i -> users.add(User.randomUser()));

        jdbc.getJdbcOperations().batchUpdate("INSERT INTO user(name, age) VALUES(?, ?)",
                users,
                10,
                new ParameterizedPreparedStatementSetter<User>() {
                    @Override
                    public void setValues(PreparedStatement ps, User user) throws SQLException {
                        ps.setString(1, user.getName());
                        ps.setInt(2, user.getAge());
                    }
                });
    }

    @Override
    public Integer create(User user) {
        throw new NotImplementedException();
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


    public NamedParameterJdbcTemplate getJdbc() {
        return jdbc;
    }

    public void setJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
