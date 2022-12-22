package com.example.demo4;

import com.example.demo4.HelpRW.PersonListTest;
import com.example.demo4.HelpRW.PersonTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private PersonOverviewController PersonController;
    public static PersonList persons;
    private PersonListTest personsTt;

    private PersonListTest personsToSave;


    private ObservableList<Person> personData =  FXCollections.observableArrayList();

    private ArrayList<Person> transform(PersonListTest obj)  {
        ArrayList <Person> newList = new ArrayList<Person>();
        for (PersonTest e : obj.personTests) {
            newList.add(new Person(e.getFirstName(), e.getLastName()));
        }
        return newList;
    }

    private void getUserList(String path) {
        personsTt = new PersonListTest();
        personsTt.readJson(path);


        persons = new PersonList();

        persons.persons = transform(personsTt);
        personData.clear();

        personData.setAll(persons.persons);

    }

    public MainApp() throws IOException {
        getUserList("persons.json");
    }


    public ObservableList<Person> getPersonData() {
        return personData;
    }
    public boolean findPerson(String searchName) {
        int ind = persons.BinarySearchByName(searchName);
        if (ind < 0) return false;

        personData.clear();
        personData.setAll(persons.persons);
        PersonController.selectIndex(ind);
        return true;
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Table Manager");

        this.primaryStage.getIcons().add(new Image("file:images/address_book_32.png"));

        initRootLayout();
        showPersonOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    public void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(personOverview);
            PersonController = loader.getController();
            PersonController.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            dialogStage.getIcons().add(new Image("file:images/edit.png"));
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showBirthdayStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);
            dialogStage.getIcons().add(new Image("file:images/calendar.png"));
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortName() {
        persons.sortName();
        personData.clear();
        personData.setAll(persons.persons);
    }
    public void showFind() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Find.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Find menu");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            Find findController = loader.getController();
            findController.setDialogStage(dialogStage);
            findController.setMainApp(this);
            dialogStage.getIcons().add(new Image("file:images/calendar.png"));

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");
            primaryStage.setTitle("AddressApp");
        }
    }


    public void loadPersonDataFromFile(File file) {
        try {
            getUserList(file.getPath());
            setPersonFilePath(file);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    private ArrayList<PersonTest> transformForSave(List<Person> obj)  {
        ArrayList <PersonTest> newList = new ArrayList<PersonTest>();
        for (Person e : obj) {
            newList.add(new PersonTest(e.getFirstName(), e.getLastName()));
        }
        return newList;
    }

    public void savePersonDataToFile(File file) {
        try {
            List<Person> showing = personData.stream().collect(Collectors.toList());
            ArrayList<PersonTest> save = transformForSave(showing);
            personsToSave = new PersonListTest();
            personsToSave.personTests = save;
            personsToSave.WriteToJsonFile(file.getPath());
            setPersonFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}