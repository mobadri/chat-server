package com.chat.server.view.server.controller;

import com.chat.server.controller.server.user.UserController;
import com.chat.server.model.user.User;
import com.chat.server.view.server.controller.confirmation.ConfirmType;
import com.chat.server.view.server.controller.confirmation.ConfirmationController;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MaintainUserViewController implements Initializable {
    @FXML
    AnchorPane rootPane;
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
    private TextField searchText;

    ObservableList<User> userList = FXCollections.observableArrayList();
    ListProperty<User> userListProperty = new SimpleListProperty<>();

    private UserController userController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public MaintainUserViewController() throws RemoteException {
    }

    private void searchTextListner(FilteredList<User> filteredData) {
        searchText.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(user -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (user.getFirstName().toLowerCase().contains(lowerCaseFilter)
                            || user.getPhone().contains(lowerCaseFilter)
                            || (user.getCountry() != null
                            && user.getCountry().toLowerCase().contains(lowerCaseFilter))
                            || (user.getLastName() != null
                            && user.getLastName().toLowerCase().contains(lowerCaseFilter))) {
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
        dobCol.setCellValueFactory(new PropertyValueFactory("dateOfBirth"));
        formatDataOnDobCol();
        BIOCol.setCellValueFactory(new PropertyValueFactory("BIO"));
    }

    private void formatDataOnDobCol() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        dobCol.setCellFactory(column -> {
            return new TableCell<User, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(formatter.format(item));
                    }
                }
            };
        });
    }

    private void loadAllUsers() {
        List<User> users = userController.getAllUsers();
        for (User u : users) {
            userList.add(u);
        }
    }

    @FXML
    private void deleteAction(ActionEvent actionEvent) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            try {

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/dialog/confirmation-view-daialog.fxml"));
                Parent root = loader.load();
                ConfirmationController confirmDialog = loader.getController();
                confirmDialog.setMessageTitle("Confirm delete User ");
                confirmDialog.setMessage("Are you sure to delete this user ? \n" +
                        user.getFirstName() + " " + user.getLastName());
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("DELETE USER ?");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                confirmDialog.setStage(stage);
                stage.showAndWait();
                if (confirmDialog.getConfirmType() == ConfirmType.OK) {
                    deleteUser(user);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void deleteUser(User user) {
        int i = userController.deleteUser(user);
        if (i > 0) {
            userList.remove(user);
        }

        System.out.println(user + " should be deleted");
    }

    @FXML
    public void newAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/userdata-view.fxml"));
            Parent root = loader.load();
            UserDataView userDataView = loader.getController();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("ADD USER");
            stage.setScene(new Scene(root));
            userDataView.setStage(stage);
            userDataView.setUser(new User());
            userDataView.setUserController(userController);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void updateAction(ActionEvent actionEvent) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            try {

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/templates/userdata-view.fxml"));
                Parent root = loader.load();
                UserDataView userDataView = loader.getController();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("ADD USER");
                stage.setScene(new Scene(root));
                userDataView.setStage(stage);
                userDataView.setUser(user);
                userDataView.setUserController(userController);
                stage.showAndWait();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void setController(UserController controller) {
        this.userController = controller;
        userListProperty.set(userList);
        usersTable.itemsProperty().bindBidirectional(userListProperty);
        usersTable.setItems(userListProperty);

        Platform.runLater(() -> {
            loadAllUsers();
            FilteredList<User> filteredData = new FilteredList<>(userList, p -> true);

            searchTextListner(filteredData);

            SortedList<User> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().
                    bind(usersTable.comparatorProperty());
            usersTable.setItems(sortedData);

        });

        setDataOnView();
    }
}