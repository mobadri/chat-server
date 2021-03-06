package com.chat.server.view.server.controller;

import com.chat.server.controller.server.user.UserController;
import com.chat.server.controller.server.user.UserControllerIntf;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class StatisticsViewController implements Initializable, UserControllerIntf {
    List<User> onlineUsersList = new ArrayList<>();
    List<User> offlineUsersList = new ArrayList<>();
    List<User> availableUsersList = new ArrayList<>();
    List<User> awayUsersList = new ArrayList<>();
    List<User> busyUsersList = new ArrayList<>();

    @FXML
    private Label availableUsersLabel;
    @FXML
    private Label offlineUsersLabel;
    @FXML
    private Label awayUsersLabel;
    @FXML
    private Label busyUsersLabel;

    @FXML
    private PieChart genderChart;
    @FXML
    private PieChart countryChart;

    private double maleCount;
    private double femaleCount;

    private Map<String, Double> countries = new HashMap<>();
    UserController userController;

    public StatisticsViewController() throws RemoteException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    private void setOnlineUsers() {
        onlineUsersList = userController.getAllOnlineUsers(true);
    }

    private void setOfflineUsers() {
        offlineUsersList = userController.getAllOnlineUsers(false);
    }

    private void fillAllUserMode(List<User> onlineUsersList) {
        for (User user : onlineUsersList) {
            switch (user.getMode()) {
                case BUSY:
                    busyUsersList.add(user);
                    break;
                case AWAY:
                    awayUsersList.add(user);
                    break;
                case AVAILABLE:
                    availableUsersList.add(user);
                    break;
            }
        }
        setModeView();
    }

    private void maleOrFemale(List<User> users) {
        for (User user : users) {
            switch (user.getGender()) {
                case MALE:
                    maleCount++;
                    break;
                case FEMALE:
                    femaleCount++;
            }
        }
    }


    private void setModeView() {
        awayUsersLabel.setText("" + awayUsersList.size());
        busyUsersLabel.setText("" + busyUsersList.size());
        availableUsersLabel.setText("" + availableUsersList.size());
        offlineUsersLabel.setText("" + offlineUsersList.size());
    }

    private void fillCountries(List<User> users) {
        Map<String, Double> stringHashMap = new HashMap<>();
        for (User user : users) {
            countries.put(user.getCountry(), 0d);
            stringHashMap.put(user.getCountry(), 0d);
        }
        for (User user : users) {
            countries.forEach((countryName, count) -> {
                if (user.getCountry().equals(countryName)) {
                    countries.put(countryName, countries.get(countryName) + 1);
                }
            });
        }
    }

    private void fillGenderChart() {
        genderChart.setTitle("Gender");
        ObservableList<PieChart.Data> genderData = FXCollections.observableArrayList(
                new PieChart.Data("Male", maleCount),
                new PieChart.Data("Female", femaleCount)
        );
        genderChart.setData(genderData);
    }

    private void fillCountryChart() {
        countryChart.setTitle("Countries");
        List<PieChart.Data> dataList = new ArrayList<>();
        countries.forEach((countryName, c) -> {
            PieChart.Data data = new PieChart.Data(countryName, c);
            data.nameProperty().setValue(data.getName() + " " + data.getPieValue());
            dataList.add(data);
        });
        ObservableList<PieChart.Data> countryData
                = FXCollections.observableArrayList(dataList);

        countryChart.setData(countryData);

    }

    @Override
    public void userChangedHisMode(User user, Mode mode) {
        // loop for all lists , remve user from the list , add user to suitable list
        //
        removeFromList(availableUsersList, user);
        removeFromList(awayUsersList, user);
        removeFromList(busyUsersList, user);

        switch (mode) {
            case AVAILABLE:
                availableUsersList.add(user);
                break;
            case AWAY:
                awayUsersList.add(user);
                break;

            case BUSY:
                busyUsersList.add(user);
                break;

        }
        setModeView();
    }

    private void removeFromList(List<User> list, User user) {
        if (list.contains(user)) {
            list.remove(user);
        }
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
        userController.setUserControllerIntf(this);
        setOnlineUsers();
        setOfflineUsers();
        fillAllUserMode(onlineUsersList);
        maleOrFemale(onlineUsersList);
        fillCountries(onlineUsersList);
        fillGenderChart();
        fillCountryChart();
    }
}
