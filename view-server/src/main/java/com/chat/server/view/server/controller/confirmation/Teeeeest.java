package com.chat.server.view.server.controller.confirmation;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Teeeeest implements Initializable {


    @FXML
    JFXTextField userNamee;
    @FXML
    JFXPasswordField passwordd;

    RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        namoo();

        mailo();


    }

    private void mailo() {
        passwordd.getValidators().add(requiredFieldValidator);
        requiredFieldValidator.setMessage("no input given");


        passwordd.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {
                    passwordd.validate();
                }
            }
        });
    }


    public void namoo() {
        userNamee.getValidators().add(requiredFieldValidator);
        requiredFieldValidator.setMessage("no input given");


        userNamee.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {
                    userNamee.validate();
                }
            }
        });
    }

    public void submitPressed(ActionEvent actionEvent) {
        System.out.println("no input");
    }
}
