package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateCustomer {

    Parent scene;
    Stage stage;

    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<?> countryCombo;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private ComboBox<?> divisionCombo;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;

    @FXML
    void cancelBtn_Action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void countryCombo_Action(ActionEvent event) {

    }

    @FXML
    void divisionCombo_Action(ActionEvent event) {

    }

    @FXML
    void saveBtn_Action(ActionEvent event) {

    }

}
