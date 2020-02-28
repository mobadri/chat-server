package com.chat.server.view.server;

import com.chat.server.controller.server.ServerControllerImpl;
import com.chat.server.view.server.controller.ServerViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/server-view.fxml"));
        Parent root = loader.load();

        ServerViewController controller = loader.getController();
        ServerControllerImpl serverController = new ServerControllerImpl();

        serverController.setServerController(controller);
        controller.setServerController(serverController);
        controller.setStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

}
