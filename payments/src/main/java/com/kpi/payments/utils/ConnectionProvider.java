package com.kpi.payments.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

    private static final String DATABASE_CONFIG_FILE = "config.properties";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return createConnection();
        } catch (Exception ex) {
            throw new RuntimeException("Exception during opening connection to database: " + ex.getMessage());
        }
    }

    private static Connection createConnection() throws SQLException {
        var properties = loadDatabaseProperties();
        String url = properties.getProperty("url");
        String username = properties.getProperty("user");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    private static Properties loadDatabaseProperties() {
        try (InputStream input = ConnectionProvider.class.getClassLoader().getResourceAsStream(DATABASE_CONFIG_FILE)) {
            var properties = new Properties();
            if (input == null) {
                throw new FileNotFoundException("Missing database properties file!");
            }
            properties.load(input);
            return properties;
        } catch (IOException ex) {
            throw new RuntimeException("Exception during reading database properties file: " + ex.getMessage());
        }
    }

    private ConnectionProvider() {
    }
}
