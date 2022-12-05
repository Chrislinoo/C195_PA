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

public class AddCustomer {
    Parent scene;
    Stage stage;

    @FXML
    private TextField addressTxtField;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<?> countryCombo;

    @FXML
    private TextField customerIdTxtField;

    @FXML
    private ComboBox<?> divisionCombo;

    @FXML
    private TextField nameTxtField;

    @FXML
    private TextField phoneTxtField;

    @FXML
    private TextField postalTxtField;

    @FXML
    private Button saveBtn;

    @FXML
    void address_input(ActionEvent event) {

    }

    @FXML
    void cancelBtn_action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void customerId_input(ActionEvent event) {

    }

    @FXML
    void name_input(ActionEvent event) {

    }

    @FXML
    void phone_input(ActionEvent event) {

    }

    @FXML
    void postal_input(ActionEvent event) {

    }

    @FXML
    void saveBtn_action(ActionEvent event) {

    }

}
