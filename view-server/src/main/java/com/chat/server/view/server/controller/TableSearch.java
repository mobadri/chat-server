package com.chat.server.view.server.controller;

import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TableSearch extends Application {
    private TableView<Member> tv = new TableView();
    private TextField tfSearch = new TextField();
    ObservableList<Member> memberList = FXCollections.observableArrayList();
    ListProperty<Member> memberListProperty = new SimpleListProperty<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TableColumn<Member, String> name = createNameColumn();
        TableColumn<Member, Integer> age = createAgeColumn();
        TableColumn<Member, String> account = createAccountColumn();
        TableColumn<Member, String> location = createLocationColumn();
        tfSearch.setPromptText("Search here");
        tv.getColumns().addAll(name, age, account, location);

        memberListProperty.set(memberList);
        tv.itemsProperty().bindBidirectional(memberListProperty);
        tv.setItems(memberListProperty);

        setData();

        FilteredList<Member> filteredData = new FilteredList<>(memberList, p -> true);
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Member.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(Member.getAge()).contains(lowerCaseFilter)) {
                    return true;
                } else if (Member.getLocation() != null && Member.getLocation().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Member.getAccount() != null && Member.getAccount().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });

        });
        SortedList<Member> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tv.comparatorProperty());
        tv.setItems(sortedData);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(tfSearch);
        borderPane.setCenter(tv);
        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableColumn createNameColumn() {
        TableColumn<Member, String> colName = new TableColumn("Name");
        colName.setMinWidth(25);
        colName.setId("colName");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        return colName;
    }

    private TableColumn createAgeColumn() {
        TableColumn<Member, Integer> colAge = new TableColumn("Age");
        colAge.setMinWidth(25);
        colAge.setId("colAge");
        colAge.setCellValueFactory(new PropertyValueFactory("age"));
        return colAge;
    }

    private TableColumn createAccountColumn() {
        TableColumn<Member, String> colAccount = new TableColumn("Account");
        colAccount.setMinWidth(25);
        colAccount.setId("colAccount");
        colAccount.setCellValueFactory(new PropertyValueFactory("account"));
        return colAccount;
    }

    private TableColumn createLocationColumn() {
        TableColumn<Member, String> colAccount = new TableColumn("Location");
        colAccount.setMinWidth(25);
        colAccount.setId("colLocation");
        colAccount.setCellValueFactory(new PropertyValueFactory("location"));
        return colAccount;
    }

    private void setData() {
        Member m = new Member();
        m.setAccount("we123");
        m.setAge(456);
        m.setLocation("Nairobi");
        m.setName("Member 1");
        memberList.add(m);

        Member m1 = new Member();
        m1.setAccount("OP5623");
        m1.setAge(321);
        m1.setLocation("Mombasa");
        m1.setName("Doe");
        memberList.add(m1);

        Member m2 = new Member();
        m2.setAge(569);
        m2.setLocation("Meru");
        m2.setName("John");
        memberList.add(m2);

        Member m3 = new Member();
        m3.setAccount("YGTR665");
        m3.setAge(666);
        m3.setLocation("Eldoret");
        m3.setName("Arif");
        memberList.add(m3);

        Member m4 = new Member();
        m4.setAccount("BHJI58966");
        m4.setAge(397);
        m4.setName("Yunus");
        memberList.add(m4);
    }

    public class Member {
        private int id;
        private String name;
        private Integer age;
        private String account;
        private String location;

        public Member() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}