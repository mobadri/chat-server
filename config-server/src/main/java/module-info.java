module config.server {
    requires java.sql;
    requires mysql.connector.java;
    opens com.chat.server.config.database to repository.server;
    exports com.chat.server.config.database to repository.server, network.server;
    requires java.naming;
}