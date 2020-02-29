package com.chat.server.view.server.controller.confirmation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/Test.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();

    }
}
