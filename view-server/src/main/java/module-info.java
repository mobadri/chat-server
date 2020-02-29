module view.server {
    requires controller.server;
    requires model.server;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.rmi;
    requires com.jfoenix;


    opens com.chat.server.view.server.controller;
    opens com.chat.server.view.server.controller.confirmation;
    opens com.chat.server.view.server;

    exports com.chat.server.view.server;
    exports com.chat.server.view.server.controller;
}