package com.db.config;

import jakarta.annotation.PreDestroy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DbConnection implements AutoCloseable
{

    private static final Log logger = LogFactory.getLog(DbConnection.class);
    private Connection connection = null;

    @Bean
    public Connection getConnection() throws SQLException // name of this func don't matter coz it will never be called. Func is there to tell the return type which is Connection instance which would be converted to a bean.
    {
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JBDL_DB1", "arad", "rootroot");  // Connection is an interface of Java that will be implemented by vendors like mysql, etc
            logger.info("DB connection started");
        }
        return connection;
    }

    @Override @PreDestroy
    public void close() throws Exception
    {
        if(connection != null && !connection.isClosed())
        {
            connection.close();
            logger.info("DB connection closed");
        }
    }

    /*
    Spring JDBC
    When JdbcTemplate instance will be created, it's constructor will get called which would look for a DataSource instance.
    Below func is forcefully creating a bean of DataSource and this bean would be used by JdbcTemplate constructor to finally create a bean of JdbcTemplate class
    Now, SpringJDBC class will be using that bean of JdbcTemplate by using @Autowire.
    */
/*
    @Bean
    public DataSource dataSource()
    {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.driverClassName("com.mysql.cj.jdbc.Driver"); // this driver (mysql-connector-j in pom) is necessary for any type of implementation like jpa, hibernate, mongodb, cassandra, etc.
        builder.url("jdbc:mysql://localhost:3306/JBDL_DB1");
        builder.username("arad");
        builder.password("rootroot");
        return builder.build();
    }
*/

    /*
    It is important in Spring proj to use above DataSource bean.
    But in case of Spring Boot proj, we have autoconfiguration and json files by which, when we add some properties in application.properties files, Spring Boot by itself will create a bean of DataSource.
    */
}

