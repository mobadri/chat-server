package com.chat.server.repository.server.message;

import com.chat.server.model.chat.Style;

public interface StyleRepository {
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
     * @param style to be deleted
     * @return int number of row deleted
     */
    int deleteStyle(Style style);
}
