package com.chat.server.repository.server.message;

import com.chat.server.model.chat.Style;

public interface StyleRepository {
    String INSERT_STYLE = "INSERT INTO style_massege (FONT_NAME,FONT_FAMILY,FONT_COLOR,back_ground," +
            "FONT_SIZE,BOLD,ITALIC,UNDER_LINE)VALUES(?,?,?,?,?,?,?,?)";
    String DELETE_STYLE = "DELETE FROM style_massege WHERE STYLE_ID=?";
    String SELECT_STYLE =  "SELECT * FROM style_massege WHERE STYLE_ID = ?";


    /**
     * find style for message by style id
     *
     * @param id style id
     * @return style of message
     */
    Style findById(int id);

    /**
     * insert style to database
     *
     * @param style to be inserted to database
     * @return style inserted
     */
    Style insertStyle(Style style);

    /**
     * delete style from database
     *
     * @param id style_id to be deleted
     * @return int number of row deleted
     */
    int deleteStyle(int id);
}
