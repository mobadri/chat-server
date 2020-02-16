module config.server {
    requires java.sql;
    requires mysql.connector.java;
    opens com.chat.server.config.database;
    exports com.chat.server.config.database;
}