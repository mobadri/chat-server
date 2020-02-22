module repository.server {
    requires java.sql;
    requires model.server;
    requires config.server;
    requires spring.security.crypto;
    exports com.chat.server.repository.server.factory to service.server;
    exports com.chat.server.repository.server.user to service.server;
    exports com.chat.server.repository.server.chat to service.server;
    exports com.chat.server.repository.server.message to service.server;
    exports com.chat.server.repository.server.notification to service.server;
}