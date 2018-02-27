package com.qdhualing.qrcodetracker.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/1/27.
 */
@Component
public class JDBCDataSource {

    private String driver;

    @Value("#{jdbcProps['jdbc.url']}")
    private String url;

    @Value("#{jdbcProps['jdbc.username']}")
    private String userName;

    @Value("#{jdbcProps['jdbc.password']}")
    private String password;


    public String getDriver() {
        return driver;
    }

    @Value("#{jdbcProps['jdbc.driverClass']}")
    public void setDriver(String driver) {
        try {
            Class.forName(driver);
            this.driver = driver;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,userName,password);
        return conn ;
    }

    public void close(Connection conn){
        if (conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
