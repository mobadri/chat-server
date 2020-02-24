package com.chat.server.config.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToMysql {

    NetworkDatabaseConfig databaseConfig;
    private static ConnectToMysql instance;
    private Connection connection;

    private ConnectToMysql() {
        databaseConfig = NetworkDatabaseConfig.getInstance();
        String databaseIP = databaseConfig.getDatabaseIP();
        String databasePortNumber = databaseConfig.getDatabasePortNumber();
        String databaseName = databaseConfig.getDatabaseName();
        String userName = databaseConfig.getUserName();
        String userPassword = databaseConfig.getUserPassword();
        String databaseURL = "jdbc:mysql://" + databaseIP + ":" + databasePortNumber + "/" + databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(databaseURL, userName, userPassword);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {

        return connection;
    }

    public static synchronized ConnectToMysql getInsetance() {
        try {
            if (instance == null) {
                instance = new ConnectToMysql();
            } else if (instance.getConnection().isClosed()) {
                instance = new ConnectToMysql();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
