module controller.server {
    exports com.chat.server.controller.server.user;
    requires service.server;
    requires model.server;
    requires java.rmi;
}