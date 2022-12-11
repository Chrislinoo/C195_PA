package controller;

import DBAccess.DBAppointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentScreen implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private Button addAppointmentBtn;

    @FXML
    private TableView<Appointments> appointmentsTable;
    @FXML
    private TableColumn<Appointments, Integer> appointmentId_column;

    @FXML
    private TableColumn<Appointments, String> contact_column;

    @FXML
    private TableColumn<Appointments, Integer> customerId_column;

    @FXML
    private Button customersBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private TableColumn<Appointments, String> description_column;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endDate_column;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endTime_column;

    @FXML
    private Button exitAppointmentBtn;

    @FXML
    private TableColumn<Appointments, String> location_column;

    @FXML
    private Button reportsBtn;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startDate_column;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startTime_column;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private TableColumn<Appointments, String> title_column;

    @FXML
    private TableColumn<Appointments, String> type_column;

    @FXML
    private Button updateAppointmentBtn;

    @FXML
    private TableColumn<Appointments, Integer> userId_column;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();

        appointmentsTable.setItems(DBAppointments.getAllAppointments());

        appointmentId_column.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title_column.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        description_column.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        location_column.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        contact_column.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type_column.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        startTime_column.setCellValueFactory(new PropertyValueFactory<>("aptStartTime"));
        endTime_column.setCellValueFactory(new PropertyValueFactory<>("aptEndTime"));
//        startDate_column.setCellValueFactory(new PropertyValueFactory<>("Start"));
//        endDate_column.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerId_column.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId_column.setCellValueFactory(new PropertyValueFactory<>("userId"));

        appointmentsTable.setItems(appointmentsObservableList);

    }

}
