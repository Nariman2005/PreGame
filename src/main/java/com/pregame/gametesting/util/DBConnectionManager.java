package com.pregame.gametesting.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static final String DB_URL;
    private static final String DB_USERNAME;
    private static final String DB_PASSWORD;

    static {
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC driver loaded successfully");

            // Load database configuration
            DB_URL = "jdbc:mysql://127.0.0.1:3306/GameDB";
            DB_USERNAME = "root";
            DB_PASSWORD = "root";

            System.out.println("Database configuration loaded successfully");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Connecting to database...");
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
  

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.err.println("Connection failed:");
            e.printStackTrace();
        }
    }
}

