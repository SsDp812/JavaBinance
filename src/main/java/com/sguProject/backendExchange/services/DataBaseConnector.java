package com.sguProject.backendExchange.services;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DataBaseConnector {
    private static String URL = "jdbc:postgresql://localhost:5432/JavaBinance";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "rootroot";

    private static Connection connection;

    public static Connection getConnection(){
        if (connection != null) {
            return connection;
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
