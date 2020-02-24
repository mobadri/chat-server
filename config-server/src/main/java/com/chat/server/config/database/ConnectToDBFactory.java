package com.chat.server.config.database;

import com.chat.server.config.database.connection.ConnectToMysql;

import java.sql.Connection;

public class ConnectToDBFactory {
    public static final Connection creatConnectionManualy() {

        return ConnectToMysql.getInsetance().getConnection();
    }

}
