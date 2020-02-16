package com.chat.server.config.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class DataSourceFactory {

    public static DataSource getSQLDataSource() {

        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        MysqlDataSource mysqlDataSource = null;
        try {
            fileInputStream = new FileInputStream("db.properties");
            properties.load(fileInputStream);
            mysqlDataSource = new MysqlDataSource();

            mysqlDataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            mysqlDataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            mysqlDataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDataSource;

    }
}


