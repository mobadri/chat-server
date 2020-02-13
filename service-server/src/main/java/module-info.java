module service.server {
    requires model.server;
    requires repository.server;
    requires serviceclientcallback;
    requires java.rmi;
    exports com.chat.server.service.server.user;
    exports com.chat.server.service.server.factory;
    exports com.chat.server.service.server.chatgroup;
    exports com.chat.server.service.server.message;
    exports com.chat.server.service.server.notification;
}