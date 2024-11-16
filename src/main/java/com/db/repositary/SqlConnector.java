package com.db.repositary;

import com.db.config.DbConnection;
import com.db.entity.Person;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SqlConnector implements DbRepo{

    Connection connection; // if used autowire instead of below constructor injection then null error would come coz DbConnection is not scanned first.

    public SqlConnector(Connection connection) {
        this.connection = connection; // instance variable "connection" is initialized to a bean of Connection class. Since in DbConnection, we have forcefully created a bean of (prebuilt) Connection class, so where ever we will create an instance of Connection class, it will be that bean only.
        createTable();
    }

    @Override
    public List<Person> getPersonData()
    {
        List<Person> list = new ArrayList<>();

        try {
            ResultSet rs = connection.createStatement().executeQuery("select * from person");
            while (rs.next()) {
                list.add(new Person(rs.getString(1), rs.getInt(2)));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public int addPerson(Person person) {
        try {
            return connection.createStatement().executeUpdate("insert into person(name, id) VALUES ('" + person.getName() + "'," + person.getId() + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addPersonByPreparedStatement(Person person)
    {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into person(name, id) VALUES(?, ?)");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getId());
            return statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /*
    For Atomic execution of the statements:
    connection.setAutoCommit(false) // default value is true. This will stage all the statements.
    connection.rollback() // to clear the staging area
    connection.commit() // commit the staged statements
    */

    public void createTable()
    {
        try {
            connection.createStatement().execute("create table if not exists person (name varchar(25), id int)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


