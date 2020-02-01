module service.server {
    exports com.chat.server.service.server.user;
    exports com.chat.server.service.server.factory;
    requires model;
    requires repository.server;
}