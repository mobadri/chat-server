package com.chat.server.view.server.controller;

import com.chat.server.controller.server.ServerController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerViewController implements Initializable, ServerController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button startButton;

    private ServerController serverController;
    private boolean running = false;
    private Stage stage;

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void showStatistics(ActionEvent actionEvent) throws IOException {
        new Thread(() -> {
            try {
                System.out.println("showStatistics");
                Parent root = FXMLLoader.load(this.getClass().getResource("/templates/statistics-view.fxml"));

                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle("User Statistics");
                    stage.setScene(new Scene(root));
                    stage.show();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void showMaintainUsers(ActionEvent actionEvent) throws IOException {
        new Thread(() -> {
            try {
                Parent root = FXMLLoader.load(this.getClass().getResource("/templates/maintain-user-view.fxml"));

                Platform.runLater(() -> {

                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle("Maintain Users");
                    stage.setScene(new Scene(root));
                    stage.show();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    @Override
    public void startServer() {
        new Thread(() -> {
            serverController.startServer();
        }).start();
    }

    @Override
    public void stopServer() {
        new Thread(() -> {
            serverController.stopServer();
        }).start();
    }

    @FXML
    public void startOrStopServer(ActionEvent actionEvent) {
        if (running) {
            stopServer();
            startButton.setText("Start");
        } else {
            startServer();
            startButton.setText("Stop");
        }
        running = !running;

    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
        System.out.println("exit");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
