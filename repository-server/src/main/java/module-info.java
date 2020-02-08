module repository.server {
    requires java.sql;
    requires model.server;
    requires config.server;
    exports com.chat.server.repository.server.factory;
    exports com.chat.server.repository.server.user;
    exports com.chat.server.repository.server.chat;
    exports com.chat.server.repository.server.adapters;
    exports com.chat.server.repository.server.message;
}