module view.server {
    requires controller.server;
    requires model.server;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;

    opens com.chat.server.view.server.controller;
    opens com.chat.server.view.server;
}