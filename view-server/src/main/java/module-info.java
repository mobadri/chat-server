module view.server {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires model;
    requires controller.server;
    opens com.chat.server.view.server.controller;
    opens com.chat.server.view.server;
}