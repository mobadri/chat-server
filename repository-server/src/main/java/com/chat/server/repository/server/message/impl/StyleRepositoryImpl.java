package com.chat.server.repository.server.message.impl;

import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.chat.Style;
import com.chat.server.repository.server.adapters.ModelAdapter;
import com.chat.server.repository.server.message.StyleRepository;

import java.sql.*;

public class StyleRepositoryImpl implements StyleRepository {
    private final    String INSERT_STYLE = "INSERT INTO style_massege (FONT_NAME,FONT_FAMILY,FONT_COLOR,back_ground," +
            "FONT_SIZE,BOLD,ITALIC,UNDER_LINE)VALUES(?,?,?,?,?,?,?,?)";
    private final String DELETE_STYLE = "DELETE FROM style_massege WHERE STYLE_ID=?";
    private final String SELECT_STYLE =  "SELECT * FROM style_massege WHERE STYLE_ID = ?";

    //@mariam
    //@todo impl this three methods to
    // select ,insert  and delete style form database
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public StyleRepositoryImpl() {
        connection = ConnectToDBFactory.creatConnectionManualy();
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
        int id =-1;
        try {
            preparedStatement = connection.prepareStatement(INSERT_STYLE,Statement.RETURN_GENERATED_KEYS);
            ModelAdapter.mapStyleTopreparedStatement(preparedStatement, style);
             preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            style.setId(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return style;
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
