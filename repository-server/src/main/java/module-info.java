module repository.server {
    requires java.sql;
    requires model.server;
    requires config.server;
    opens com.chat.server.repository.server.user.impl;
    opens com.chat.server.repository.server.chat.impl;
    opens com.chat.server.repository.server.message.impl;
    opens com.chat.server.repository.server.notification.impl;
    exports com.chat.server.repository.server.factory;
    exports com.chat.server.repository.server.user;
    exports com.chat.server.repository.server.chat;
    exports com.chat.server.repository.server.adapters;
    exports com.chat.server.repository.server.message;
    exports com.chat.server.repository.server.notification;
}