package controller;

import DBAccess.DBAppointments;
import Database.JDBC;
import javafx.collections.FXCollections;
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
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller for the appointmentScreen.fxml file. It's responsible for all the elements in the screen.
 */
public class AppointmentScreen implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private Button addAppointmentBtn;
    @FXML
    private ToggleGroup filter;
    @FXML
    private RadioButton weekRadioButton;
    @FXML
    private RadioButton monthRadioButton;
    @FXML
    private RadioButton allRadioButton;

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

    /**
     * Redirects user to the add appointment screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void addBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));

        stage.setScene(new Scene(scene));
        stage.setTitle("Add Appointment");

        stage.show();
    }

    /**
     * Redirects user to the Customers page.
     * @param event
     * @throws IOException
     */
    @FXML
    void customersBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));

        stage.setScene(new Scene(scene));
        stage.setTitle("Customer Screen");

        stage.show();
    }


    /**
     * Deletes a selected appointment. The event starts when the delete button is clicked depending on if there was already
     * a selection made. If there was not then it should catch an error message. The deletion is made by appointment ID.
     * Which will also be presented in the confirmation warning as well as the type of appointment.
     * @param event
     * @throws IOException
     */
    @FXML
    void deleteBtnAction(ActionEvent event) throws SQLException {



        try {
            int selectionID = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId();
            String selectionType = appointmentsTable.getSelectionModel().getSelectedItem().getAptType();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the appointment ID: " +selectionID+ " of type: " +selectionType+ ", do you wish to continue?");
            alert.setTitle("Delete");
            Optional<ButtonType> result = alert.showAndWait();

            Connection connection = JDBC.connection;
            int selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBAppointments.delete(selectedAppointment);
                ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();
                appointmentsTable.setItems(appointmentsObservableList);
            }

        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select an appointment first");
            alert.showAndWait();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Exits and closes the program.
     * @param event
     */
    @FXML
    void exitBtnAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Redirects the user to the Reports page.
     * @param event
     * @throws IOException
     */
    @FXML
    void reportsBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/reportsContact.fxml"));

        stage.setScene(new Scene(scene));
        stage.setTitle("Reports");

        stage.show();
    }

    /**
     * Updates a selected appointment. The event starts when the update button is clicked depending on if there was already
     * a selection made. If there was not then it should catch an error message. It uses the appointmentTransfer method
     * which takes in (index, appointment) as parameters.
     * @param event
     * @throws IOException
     */
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
            stage.setTitle("Update Appointment");
            stage.show();

        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select an appointment first");
            alert.showAndWait();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Initializes the tableview with the necessary information. The data is pulled from the getAllAppointments method
     * in the DBAppointments java class.
     * @param url
     * @param resourceBundle
     */
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

    /**
     * Filters table to show appointments within a months time frame of current time.
     * @param actionEvent
     */
    public void monthRadioButton_Action(ActionEvent actionEvent) {
        ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();
        ObservableList<Appointments> appointmentsByMonth = FXCollections.observableArrayList();
        LocalDateTime startOfMonth = LocalDateTime.now().minusMonths(1);
        LocalDateTime endOfMonth = LocalDateTime.now().plusMonths(1);

        if (appointmentsObservableList != null){
            appointmentsObservableList.forEach(appointments -> {
                if (appointments.getAptEndTime().isAfter(startOfMonth) && appointments.getAptEndTime().isBefore(endOfMonth)){
                    appointmentsByMonth.add(appointments);
                }//sets in the appointmentsByMonth list with all the appointments that is after the start of the month and before the end of the month.
                appointmentsTable.setItems(appointmentsByMonth);
            });
        }

    }

    /**
     * Filters table to show appointments within a weeks time frame of current time.
     * @param actionEvent
     */
    public void weekRadioButton_Action(ActionEvent actionEvent) {
        ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();
        ObservableList<Appointments> appointmentsByWeek = FXCollections.observableArrayList();
        LocalDateTime startOfWeek =LocalDateTime.now().minusWeeks(1);
        LocalDateTime endOfWeek = LocalDateTime.now().plusWeeks(1);

        if (appointmentsObservableList != null) {
            appointmentsObservableList.forEach(appointments -> {
                if (appointments.getAptEndTime().isAfter(startOfWeek) && appointments.getAptEndTime().isBefore(endOfWeek)){
                    appointmentsByWeek.add(appointments);
                }//sets in the appointmentsByWeek list with all the appointments that is after the start of the week and before the end of the week.
                appointmentsTable.setItems(appointmentsByWeek);
            });
        }
    }

    /**
     * Resets table to show all data, regardless of time frame.
     * @param actionEvent
     */
    public void allRadioButton_Action(ActionEvent actionEvent) {
        ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();

        if (appointmentsObservableList != null){
            appointmentsTable.setItems(appointmentsObservableList);//sets it to normal
        }
    }



}


