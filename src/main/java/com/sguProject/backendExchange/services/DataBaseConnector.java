package com.sguProject.backendExchange.services;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DataBaseConnector {
    private static String dbURL = "jdbc:postgresql://localhost:5432/testBase";
    private static String userName = "postgres";
    private static String password = "rootroot";

    public static Connection getConnect(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURL,userName,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
