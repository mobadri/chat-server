module model.server {
    opens com.chat.server.model.chat;
    opens com.chat.server.model.user;
    exports com.chat.server.model.chat;
    exports com.chat.server.model.user;
}