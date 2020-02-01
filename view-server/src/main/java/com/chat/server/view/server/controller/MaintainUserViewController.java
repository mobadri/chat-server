package com.chat.server.view.server.controller;

import com.chat.server.controller.server.user.UserController;
import com.chat.server.model.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MaintainUserViewController implements Initializable {
    UserController controller = new UserController();

    @FXML
    TableView usersTable;
    @FXML
    TableColumn firstNameCol;
    @FXML
    TableColumn lastNameCol;
    @FXML
    TableColumn phoneCol;
    @FXML
    TableColumn mailCol;
    @FXML
    TableColumn countryCol;
    @FXML
    TableColumn genderCol;
    @FXML
    TableColumn dobCol;
    @FXML
    TableColumn BIOCol;

    ObservableList<User> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllUsers();
        initializeTable();
    }

    private void initializeTable() {
        setDataOnView(getInitialTableData());
    }


    private void setDataOnView(ObservableList<User> data) {
        usersTable.setItems(data);
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory("phone"));
        mailCol.setCellValueFactory(new PropertyValueFactory("mail"));
        countryCol.setCellValueFactory(new PropertyValueFactory("country"));
        genderCol.setCellValueFactory(new PropertyValueFactory("gender"));
        dobCol.setCellValueFactory(new PropertyValueFactory("dateOfBirth"));
        BIOCol.setCellValueFactory(new PropertyValueFactory("BIO"));
    }

    private ObservableList getInitialTableData() {
        List<User> users = controller.getAllUsers();
        return FXCollections.observableList(users);
    }

    private void loadAllUsers() {

    }
}
