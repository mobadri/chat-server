module controller.server {
    exports com.chat.server.controller.server.user;
    exports com.chat.server.controller.server;
    requires service.server;
    requires model.server;
    requires java.rmi;
    requires network.server;
}