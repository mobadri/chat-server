package com.chat.server.view.server.controller;

import com.chat.server.controller.server.user.UserController;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
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
    public Label InvalidPassword;
    @FXML
    public Label InvalidConfirmPassword;
    @FXML
    public Label InvalidDateOfBirth;
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
    public JFXPasswordField password;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    public JFXPasswordField confirmPassword;
    @FXML
    private JFXComboBox country;
    @FXML
    private JFXTextField email;
    @FXML
    public JFXDatePicker dateOfBirthh;
    @FXML
    private JFXTextField bio;
    @FXML
    private Label title;
    @FXML
    private JFXTextField phone;


    private Gender gender = Gender.MALE;
    private User user;

    private Stage stage;
    private UserController userController;

    private RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
    private boolean updateFlag = true;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllCountries();
        checkAllField();
    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }


    private void checkAllField() {
        requiredFieldValidator.setMessage("*No Input Field");
        firstName.getValidators().add(requiredFieldValidator);
        firstName.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                firstName.validate();
            }
        });

        lastName.getValidators().add(requiredFieldValidator);
        lastName.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                lastName.validate();
            }
        });

        password.getValidators().add(requiredFieldValidator);
        password.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                password.validate();
            }
        });

        confirmPassword.getValidators().add(requiredFieldValidator);
        confirmPassword.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                confirmPassword.validate();
            }
        });

        email.getValidators().add(requiredFieldValidator);
        email.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                email.validate();
            }
        });

        phone.getValidators().add(requiredFieldValidator);
        phone.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                phone.validate();
            }
        });

        country.getValidators().add(requiredFieldValidator);
        country.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                country.validate();
            }
        });

        dateOfBirthh.getValidators().add(requiredFieldValidator);
        dateOfBirthh.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                dateOfBirthh.validate();
            }
        });

        bio.getValidators().add(requiredFieldValidator);
        bio.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1) {
                bio.validate();
            }
        });

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
        if (user.getId() > 0) {
            user.setId(user.getId());
        }
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setPassword(password.getText());
        user.setEmail(email.getText());
        user.setPhone(phone.getText());
        System.out.println(country.getValue() + " country");
        user.setCountry(country.getValue() == null ? "Egypt" : country.getValue().toString());
        user.setDateOfBirth(dateOfBirthh.getValue());
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
        if (user.getId() > 0) {
            phone.setEditable(false);
        }
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
        firstName.setStyle("-fx-border-color: white; -fx-border-width: 1px ;");
        lastName.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        phone.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        password.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        confirmPassword.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        email.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");
        country.setStyle("-fx-border-color: white ; -fx-border-width: 1px ;");

    }

    private void setError(String key, Boolean value) {
        switch (key) {
            case "InvalidFirstName":
                InvalidFirstName.setText("* Invalid First Name");
                break;

            case "InvalidLastName":
                InvalidLastName.setText("* Invalid Last Name");
                break;

            case "InvalidPhone":
                InvalidPhone.setText("*Invalid Phone");
                break;

            case "InvalidPassword":
                InvalidPassword.setText("* Weak Pass At least 8 character");
                break;

            case "InvalidEmail":
                InvalidEmail.setText("* Invalid Email");
                break;

            case "InvalidCountry":
                InvalidCountry.setText("*Invalid Country");
                break;

            case "InvalidDateOfBirth":
                InvalidDateOfBirth.setText("*Invalid DateOfBirth");
                break;

        }

    }

    public void insertNewUser() {
        User insertedUser = userController.insertUser(user);
        if (insertedUser != null && insertedUser.getId() > 0) {
            showMessageDialog(Alert.AlertType.INFORMATION, "user has been added successfully");
            returnToParent();
        } else {
            showMessageDialog(Alert.AlertType.ERROR, "error on register user call us !");
        }
    }


    private void updateUser() {
        user = setUserData();
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
        if (password.getText() != null && confirmPassword.getText() != null && password.getText().equals(confirmPassword.getText())) {
            Map<String, Boolean> validationMap = new HashMap<>();
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
                    return true;
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
        boolean isValid = validateUser(setUserData());
        if (user != null && user.getId() > 0) {
            System.out.println("update");
            if (isValid) {
                updateUser();
            }
        } else {
            if (isValid) {
                insertNewUser();
            }
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

