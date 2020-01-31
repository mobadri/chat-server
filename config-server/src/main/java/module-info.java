module config.server {
    requires java.sql;
    opens com.chat.server.config.database;
    exports com.chat.server.config.database;
}