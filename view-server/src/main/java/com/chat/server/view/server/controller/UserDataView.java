package com.chat.server.view.server.controller;

import com.chat.server.controller.server.user.UserController;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class UserDataView implements Initializable {


    @FXML
    public JFXPasswordField password;
    @FXML
    public JFXPasswordField confirmPassword;
    @FXML
    public Label InvalidPassword;
    @FXML
    public Label InvalidConfirmPassword;
    @FXML
    public Label InvalidDateOfBirth;
    UserController userController;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXComboBox country;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField dateOfBirth;
    @FXML
    private JFXTextField bio;
    private Gender gender = Gender.MALE;
    @FXML
    private Label InvalidFirstName;
    @FXML
    private Label InvalidLastName;
    @FXML
    private Label InvalidPhone;
    @FXML
    private Label InvalidEmail;
    @FXML
    private Label InvalidCountry;
    private Stage stage;

    {
        try {
            userController = new UserController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllCountries();
    }

    private void loadAllCountries() {
        List<String> collect = Arrays.asList(Locale.getAvailableLocales())
                .parallelStream().map(Locale::getDisplayCountry)
                .filter(s -> !s.isEmpty())
                .sorted()
                .collect(Collectors.toList());
        country.setItems(FXCollections.observableList(collect));
    }


    private User setUserData() {
        User user = new User();
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        //   if (password.getText().equals(confirmPassword.getText())) {
        user.setPassword(password.getText());

        user.setEmail(email.getText());
        user.setPhone(phone.getText());
        user.setCountry(country.getSelectionModel().getSelectedItem().toString());
        user.setDateOfBirth(new Date(dateOfBirth.getText()));
        user.setBIO(bio.getText());
        if (male.isSelected()) {
            user.setGender(Gender.MALE);
        } else if (female.isSelected()) ;
        user.setGender(Gender.FEMALE);
        user.setOnline(false);
        user.setMode(Mode.AVAILABLE);

        return user;
        //} else {
        //  InvalidConfirmPassword.setText("* Invalid Password");
        //  password.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        //   return null;
    }


    private void clearValidation() {


        InvalidFirstName.setText("");
        InvalidLastName.setText("");
        InvalidPhone.setText("");
        InvalidPassword.setText("");
        InvalidEmail.setText("");
        InvalidCountry.setText("");
        firstName.setStyle("-fx-border-color: gray; -fx-border-width: 1px ;");
        lastName.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
        phone.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
        password.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
        email.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");
        country.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");

    }


    private void setError(String key, Boolean value) {
        switch (key) {
            case "InvalidFirstName":
                InvalidFirstName.setText("* Invalid First Name");
                firstName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                break;
            case "InvalidLastName":
                InvalidLastName.setText("* Invalid Last Name");
                lastName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                break;
            case "InvalidPhone":
                InvalidPhone.setText("*Invalid Phone");
                phone.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                break;
            case "InvalidPassword":
                InvalidPassword.setText("* Weak Pass At least 8 character");
                password.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                break;
            case "InvalidEmail":
                InvalidEmail.setText("* Invalid Email");
                email.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");

                break;
            case "InvalidCountry":
                InvalidCountry.setText("*Invalid Country");
                country.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                break;

        }

    }


    public void addUserAction(ActionEvent actionEvent) {
        clearValidation();
        if (password.getText().equals(confirmPassword.getText())) {

            Map<String, Boolean> validationMap = new HashMap<>();
            User user = setUserData();
            if (user != null) {

                Map<String, Boolean> validateMap = userController.validateUser(user);
                validateMap.forEach((key, valid) -> {
                    if (!valid) {
                        validationMap.put(key, valid);
                    }
                });
                if (validationMap.size() > 0) {
                    validationMap.forEach((key, value) -> {
                        setError(key, value);
                        System.out.println(key + "" + value);
                    });
                } else {
                    userController.insertUser(user);
                    System.out.println("user added");
                }
            }
        } else {
            InvalidPassword.setText("* Invalid Password");
            password.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
    }

}









