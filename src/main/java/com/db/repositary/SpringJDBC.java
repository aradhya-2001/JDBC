package com.db.repositary;

import com.db.config.DbConnection;
import com.db.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpringJDBC implements DbRepo{

    /*
    Connection pool will be created here to connect to underlying Db i.e. MySql.
    Whenever we will run a query then a connection (from the connection pool) to our db will be formed and when query is over, it will get closed.
    */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate; // it's like prepared statements.

    @Override
    public List<Person> getPersonData() {
       return jdbcTemplate.query("select * from person", new RowMapper<Person>() { // doing ORM

           @Override
           public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
               return Person.builder().id(rs.getInt(2)).name(rs.getString(1)).build(); // here order of writing rs will not matter as compared to the case of SqlConnector file.
           }
       });
    }

    @Override
    public int addPerson(Person person) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", person.getName());
        source.addValue("id", person.getId());
        return namedParameterJdbcTemplate.update("insert into person (name, id) VALUES (:name, :id)", source); // :name = person.getName()
    }

    @Override
    public int addPersonByPreparedStatement(Person person) {
        return 0;
    }
}
