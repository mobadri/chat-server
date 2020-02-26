package com.chat.server.view.server.controller;

import com.chat.server.controller.server.user.UserController;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class UserDataView implements Initializable {
    @FXML
    public JFXButton add;
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
    public JFXDatePicker dateOfBirthh;

    @FXML
    private JFXTextField bio;
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
    @FXML
    private Label title;
    private Stage stage;
    private Gender gender = Gender.MALE;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadAllCountries();
//        addPhoneActionListner();
    }

//    private void addPhoneActionListner() {
////        phone.textProperty().addListener(new ChangeListener<String>() {
////            @Override
////            public void changed(ObservableValue<? extends String> observable, String oldValue,
////                                String newValue) {
////                if (!newValue.matches("^(?:\\+?2)?01[0-9]{9}$")) {
////                    phone.setText(newValue.replaceAll("[\\D]", ""));
////                }
////            }
////        });
//
//    }

    private void loadAllCountries() {
        List<String> collect = Arrays.asList(Locale.getAvailableLocales())
                .parallelStream().map(Locale::getDisplayCountry)
                .filter(s -> !s.isEmpty())
                .sorted()
                .collect(Collectors.toList());
        country.setItems(FXCollections.observableList(collect));
    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }


    private User getUserData() {
        if (user.getId() > 0) {
            user.setId(user.getId());
        }
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setPassword(password.getText());
        user.setEmail(email.getText());
        user.setPhone(phone.getText());
        user.setCountry(country.getSelectionModel().getSelectedItem().toString());
        user.setDateOfBirth(LOCAL_DATE("01-05-2016"));
        user.setBIO(bio.getText());
        if (male.isSelected()) {
            user.setGender(Gender.MALE);
        } else if (female.isSelected()) ;
        user.setGender(Gender.FEMALE);
        user.setOnline(false);
        user.setMode(Mode.AVAILABLE);
        return user;
    }

    private void setViewData(User user) {
        if (user.getId() > 0) {
            title.setText("Update user " + user.getFirstName() + " " + user.getLastName());
            add.setText("Update");
        }
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        password.setText(user.getPassword());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        country.setValue(user.getCountry());
        dateOfBirthh.setValue(user.getDateOfBirth());
        bio.setText(user.getBIO());
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

    public void insertNewUser() {
        clearValidation();
        if (password.getText() != null &&
                confirmPassword.getText() != null &&
                password.getText().equals(confirmPassword.getText())) {

            Map<String, Boolean> validationMap = new HashMap<>();
            User user = getUserData();
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
                    });
                } else {
                    User insertedUser = userController.insertUser(user);
                    if (insertedUser.getId() > 0) {
                        showMessageDialog(Alert.AlertType.INFORMATION, "user has been added successfully");
                        returnToParent();
                    } else {
                        showMessageDialog(Alert.AlertType.ERROR, "error on register user call us !");
                    }
                }
            }
        } else {
            InvalidPassword.setText("* Invalid Password");
            password.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
    }

    private void updateUser() {
        user = getUserData();
        User update = userController.updateUser(user, user.getPassword());
        if (update != null) {
            showMessageDialog(Alert.AlertType.INFORMATION, "user has been updated successfully");
            returnToParent();
        } else {
            showMessageDialog(Alert.AlertType.ERROR, "error on update user call us !");
        }
    }

    private boolean validateUser(User user) {
        clearValidation();
        if (password.getText() != null &&
                confirmPassword.getText() != null &&
                password.getText().equals(confirmPassword.getText())) {

            Map<String, Boolean> validationMap = new HashMap<>();
            user = getUserData();
            if (user != null) {
                System.out.println(userController);
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
                }
            }
        } else {
            InvalidPassword.setText("* Invalid Password");
            password.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        }
        return false;

    }

    @FXML
    public void addUserAction(ActionEvent actionEvent) {
        System.out.println("update inuseraction");
        System.out.println(user);
        if (user != null && user.getId() > 0) {
            System.out.println("update");
            if (!validateUser(getUserData())) {
                updateUser();
            }
        } else {
            insertNewUser();
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void setUser(User user) {
        this.user = user;
        setViewData(user);
    }

    private void showMessageDialog(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void returnToParent() {
        stage.close();
    }

}









