package com.gmail.at.ivanehreshi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserSqlQuery_MappingSqlQuery extends MappingSqlQuery<User>{

    @Autowired
    public UserSqlQuery_MappingSqlQuery(DataSource dataSource) {
        setDataSource(dataSource);
        setSql("SELECT id, name,age FROM user WHERE id = ?");
        declareParameter(new SqlParameter("id", Types.INTEGER));
    }

    @Override
    protected User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("name"), rs.getInt("age"));
    }
}
