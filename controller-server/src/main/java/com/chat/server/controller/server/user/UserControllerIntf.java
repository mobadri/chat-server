package com.chat.server.controller.server.user;

import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;

public interface UserControllerIntf {
    /**
     * change the status of client-user
     *
     * @param user-client
     */
    void userChangedHisMode(User user, Mode mode);

    //userchange his status(User user, boolean isonline);
}
