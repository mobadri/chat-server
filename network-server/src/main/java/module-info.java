module network.server {
    exports com.chat.server.network.server;

    requires java.rmi;
    requires service.server;
    requires config.server;
}