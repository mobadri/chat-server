package com.chat.server.view.server.controller;

import com.chat.server.controller.server.user.UserController;
import com.chat.server.model.user.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MaintainUserViewController implements Initializable {
    UserController controller = new UserController();

    @FXML
    TableView<User> usersTable;
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

    @FXML
    TextField searchText;

    ObservableList<User> userList = FXCollections.observableArrayList();
    ListProperty<User> userListProperty = new SimpleListProperty<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userListProperty.set(userList);
        usersTable.itemsProperty().bindBidirectional(userListProperty);
        usersTable.setItems(userListProperty);

        loadAllUsers();

        FilteredList<User> filteredData = new FilteredList<>(userList, p -> true);
        searchTextListner(filteredData);
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(usersTable.comparatorProperty());
        usersTable.setItems(sortedData);
        setDataOnView();


    }

    private void searchTextListner(FilteredList<User> filteredData) {
        searchText.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(Member -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (Member.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (Member.getPhone().contains(lowerCaseFilter)) {
                        return true;
                    } else if (Member.getCountry() != null
                            && Member.getCountry().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (Member.getLastName() != null
                            && Member.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                }));

    }

    private void setDataOnView() {
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory("phone"));
        mailCol.setCellValueFactory(new PropertyValueFactory("email"));
        countryCol.setCellValueFactory(new PropertyValueFactory("country"));
        genderCol.setCellValueFactory(new PropertyValueFactory("gender"));
        //todo cell factory for date
        dobCol.setCellValueFactory(new PropertyValueFactory("dateOfBirth"));
        BIOCol.setCellValueFactory(new PropertyValueFactory("BIO"));
    }

    private void loadAllUsers() {
        List<User> users = controller.getAllUsers();
        for (User u : users) {
            userList.add(u);
        }
    }
}
