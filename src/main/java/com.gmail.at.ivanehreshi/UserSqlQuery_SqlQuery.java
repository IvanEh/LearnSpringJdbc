package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.util.Map;

public class UserSqlQuery_SqlQuery extends SqlQuery<User> {

    @Autowired
    public UserSqlQuery_SqlQuery(DataSource dataSource) {
        setDataSource(dataSource);
        setSql("SELECT id, name, age FROM user WHERE id = ?");
        declareParameter(new SqlParameter("id", Types.INTEGER));
    }

    @Override
    protected RowMapper<User> newRowMapper(Object[] parameters, Map<?, ?> context) {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getString("name"), rs.getInt("age"));
            }
        };
    }
}
