package com.chat.server.view.server.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerViewController implements Initializable {
    @FXML
    private AnchorPane anchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void showStatistics(ActionEvent actionEvent) throws IOException {
        System.out.println("showStatistics");
        Parent root = FXMLLoader.load(this.getClass().getResource("/templates/statistics-view.fxml"));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("User Statistics");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    private void showMaintainUsers(ActionEvent actionEvent) throws IOException {
        System.out.println("showMaintainUsers");
        Parent root = FXMLLoader.load(this.getClass().getResource("/templates/chat-view.fxml"));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Maintain Users");
        stage.setScene(new Scene(root));
        stage.show();

    }

}
