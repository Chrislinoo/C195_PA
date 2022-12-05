package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAppointment {

    Parent scene;
    Stage stage;
    @FXML
    private TextField appointmentIdTxtField;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField contactTxtField;

    @FXML
    private TextField customerIdTxtField;

    @FXML
    private TextField descriptionTxtField;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField locationTxtField;

    @FXML
    private Button saveBtn;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField titileTxtField;

    @FXML
    private TextField typeTxtField;

    @FXML
    private TextField userIdTxtField;

    @FXML
    void appointmentId_action(ActionEvent event) {

    }

    @FXML
    void cancelBtn_action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void contact_action(ActionEvent event) {

    }

    @FXML
    void customerId_action(ActionEvent event) {

    }

    @FXML
    void description_action(ActionEvent event) {

    }

    @FXML
    void endDate_action(ActionEvent event) {

    }

    @FXML
    void location_action(ActionEvent event) {

    }

    @FXML
    void saveBtn_action(ActionEvent event) {

    }

    @FXML
    void startDate_action(ActionEvent event) {

    }

    @FXML
    void title_action(ActionEvent event) {

    }

    @FXML
    void type_action(ActionEvent event) {

    }

    @FXML
    void userId_action(ActionEvent event) {

    }

}
