package com.chat.server.config.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToMysql {


    private static ConnectToMysql instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/chatDB";
    private String username = "root";
    private String password = "root";

    private ConnectToMysql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);

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
