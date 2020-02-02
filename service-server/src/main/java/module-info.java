module service.server {
    requires model.server;
    requires repository.server;
    exports com.chat.server.service.server.user;
    exports com.chat.server.service.server.factory;
}