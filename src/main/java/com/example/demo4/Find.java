package com.example.demo4;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Find {
    @FXML
    private TextField inpText;
    private Stage dialogStage;

    private MainApp mainApp;

    @FXML
    private void initialize() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleFind() {
        if (isInputValid()) {
            if (!mainApp.findPerson(inpText.getText())) {
                String errorMessage = "";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Search failed");
                alert.setHeaderText("Please input correct name!");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            }
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (inpText.getText() == null || inpText.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
