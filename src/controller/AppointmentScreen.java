package controller;

import DBAccess.DBAppointments;
import Database.JDBC;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

//    public int delete(int appointment_Id) throws SQLException {
//        String deleteSql = "DELETE FROM appointments WHERE Appointment_ID = ?";
//        PreparedStatement ps = JDBC.connection.prepareStatement(deleteSql);
//
//        UpdateAppointment deleteAppointment =  new UpdateAppointment();
//        deleteAppointment.appointmentTransfer(appointmentsTable.getSelectionModel().getSelectedIndex(), appointmentsTable.getSelectionModel().getSelectedItem());
//
//        ps.setInt(1,appointment_Id);
//
//
//        int rowsAffected = ps.executeUpdate();
//
//        return rowsAffected;
//    }

    @FXML
    void deleteBtnAction(ActionEvent event) throws SQLException {
        try {

            Connection connection = JDBC.connection;
            int selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId();

            DBAppointments.delete(selectedAppointment);
            ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();
            appointmentsTable.setItems(appointmentsObservableList);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateAppointment.fxml"));
            loader.load();

            UpdateAppointment updateAppointment = loader.getController();
            updateAppointment.appointmentTransfer(appointmentsTable.getSelectionModel().getSelectedIndex(), appointmentsTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();







        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        customerId_column.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId_column.setCellValueFactory(new PropertyValueFactory<>("userId"));

        appointmentsTable.setItems(appointmentsObservableList);

    }

}
