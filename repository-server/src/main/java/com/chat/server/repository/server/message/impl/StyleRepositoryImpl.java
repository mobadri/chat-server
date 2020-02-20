package com.chat.server.repository.server.message.impl;

import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.chat.Style;
import com.chat.server.repository.server.adapters.ModelAdapter;
import com.chat.server.repository.server.message.StyleRepository;

import java.sql.*;

public class StyleRepositoryImpl implements StyleRepository {
    //@mariam
    //@todo impl this three methods to
    // select ,insert  and delete style form database
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public StyleRepositoryImpl() {
        connection = ConnectToDBFactory.creatConnectionManualy();
    }

    public static void main(String[] args) {
        StyleRepositoryImpl s = new StyleRepositoryImpl();
        System.out.println(s.connection);
        Style style = new Style();


        System.out.println(s.insertStyle(new Style("POIUYTRE","fdUUUUU","sdJJf",
                "fdcg",45,true,true,true)));
        System.out.println(s.findById(3));
        System.out.println(s.deleteStyle(1));
    }

    @Override
    public Style findById(int id) {
        Style style = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_STYLE);
            System.out.println(preparedStatement);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            style = ModelAdapter.mapResulsetTosTyle(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return style;
    }

    @Override
    public Style insertStyle(Style style) {
        int res = 0;
        try {
            preparedStatement = connection.prepareStatement(INSERT_STYLE);
            ModelAdapter.mapStyleTopreparedStatement(preparedStatement, style);

            preparedStatement.setInt(1, style.getId());
            res = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res != 0 ? style : null;
    }

    @Override
    public int deleteStyle(int id) {
        int res = 0;
        try {
            preparedStatement = connection.prepareStatement(DELETE_STYLE);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
