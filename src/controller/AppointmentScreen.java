package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentScreen {

    Parent scene;
    Stage stage;

    @FXML
    private Button addAppointmentBtn;

    @FXML
    private TableColumn<?, ?> appointmentId_column;

    @FXML
    private TableColumn<?, ?> contact_column;

    @FXML
    private TableColumn<?, ?> customerId_column;

    @FXML
    private Button customersBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private TableColumn<?, ?> description_column;

    @FXML
    private TableColumn<?, ?> endDate_column;

    @FXML
    private TableColumn<?, ?> endTime_column;

    @FXML
    private Button exitAppointmentBtn;

    @FXML
    private TableColumn<?, ?> location_column;

    @FXML
    private Button reportsBtn;

    @FXML
    private TableColumn<?, ?> startDate_column;

    @FXML
    private TableColumn<?, ?> startTime_column;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TableColumn<?, ?> title_column;

    @FXML
    private TableColumn<?, ?> type_column;

    @FXML
    private Button updateAppointmentBtn;

    @FXML
    private TableColumn<?, ?> userId_column;

    @FXML
    private SplitMenuButton viewBySplit;

    @FXML
    void addBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void customersBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void deleteBtnAction(ActionEvent event) {

    }

    @FXML
    void exitBtnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void reportsBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/reportsContact.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void splitMenuAll(ActionEvent event) {

    }

    @FXML
    void splitMenuMonth(ActionEvent event) {

    }

    @FXML
    void splitmenuWeek(ActionEvent event) {

    }

    @FXML
    void updateBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/updateAppointment.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();

    }

}
