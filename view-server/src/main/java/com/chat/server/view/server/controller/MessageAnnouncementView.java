package com.chat.server.view.server.controller;

import com.chat.server.controller.server.AnnouncementController;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageAnnouncementView implements Initializable {
    @FXML
    private JFXTextField announcementMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void sendAnnouncement(ActionEvent actionEvent) {
        System.out.println("Button click");
        Notification notification = new Notification();
        // add notification data
        notification.setNotificationType(NotificationType.ANNOUNCEMENT_RECEIVED);
        notification.setNotificationMessage(announcementMessage.getText());

        // add user data
        User user = new User();
        user.setId(1);
        user.setOnline(true);
        user.setMode(Mode.AVAILABLE);
        user.setPhone("01150777011");
        user.setFirstName("System");
        user.setLastName("Admin");
        notification.setUserFrom(user);

        // call notification controller, pass the notification
        AnnouncementController announcementController = new AnnouncementController();
        announcementController.sendAnnouncementNotification(notification);
    }
}
