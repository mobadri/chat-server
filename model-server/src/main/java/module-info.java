module model.server {
    requires java.xml.bind;
    exports com.chat.server.model.chat.xmlmessage;
    opens com.chat.server.model.chat.xmlmessage;
    opens com.chat.server.model.chat;
    opens com.chat.server.model.user;
    exports com.chat.server.model.chat;
    exports com.chat.server.model.user;
}