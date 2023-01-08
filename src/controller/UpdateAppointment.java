package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customer;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateAppointment implements Initializable{

    public ComboBox contactCombo;
    public ComboBox startTimeCombo;
    public ComboBox endTimeCombo;
    public ComboBox customerCombo;
    public ComboBox userCombo;
    Parent scene;

    Stage stage;

    @FXML
    private TextField aptIdTxt;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField contactTxt;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField endTimeTxt;

    @FXML
    private TextField locationTxt;

    @FXML
    private Button saveBtn;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField startTimeTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private TextField userIdTxt;
    private Appointments selectedAppointment;
    private int selectedIndex;

    @FXML
    void cancelBtn_Action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.setTitle("Appointment Screen");

        stage.show();
    }

    @FXML
    void saveBtn_Action(ActionEvent event) {

        try {
            String updateSql = "UPDATE appointments SET Appointment_ID = ?, Description = ?, Contact_ID = ?, Start = ?, End = ?, Title = ?, Location = ?, Type = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(updateSql);

            LocalDate startDate = startDatePicker.getValue();
            LocalTime startTime = LocalTime.parse(startTimeCombo.getValue().toString());
            LocalDateTime startDateTime = LocalDateTime.of(startDate,startTime);

            LocalDate endDate = endDatePicker.getValue();
            LocalTime endTime = LocalTime.parse(endTimeCombo.getValue().toString());
            LocalDateTime endDateTime = LocalDateTime.of(endDate,endTime);

            ps.setInt(1, Integer.parseInt(aptIdTxt.getText()));
            ps.setString(2,descriptionTxt.getText());
            ps.setInt(3, Integer.parseInt(contactCombo.getValue().toString()));
            ps.setTimestamp(4, Timestamp.valueOf(startDateTime));//--FIXED
            ps.setTimestamp(5,Timestamp.valueOf(endDateTime));//--FIXED
            ps.setString(6,titleTxt.getText());
            ps.setString(7,locationTxt.getText());
            ps.setString(8,typeTxt.getText());
            ps.setTimestamp(9,Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10,"admin");
            ps.setTimestamp(11,Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(12, Integer.parseInt(userCombo.getValue().toString()));
            ps.setInt(13, Integer.parseInt(customerCombo.getValue().toString()));
            ps.setInt(14,Integer.parseInt(userCombo.getValue().toString()));
            ps.setInt(15, Integer.parseInt(aptIdTxt.getText()));

            ps.execute();

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void appointmentTransfer(int index, Appointments appointments){
        selectedAppointment = appointments;
        selectedIndex = index;

        this.aptIdTxt.setText(String.valueOf(appointments.getAppointmentId()));
        this.descriptionTxt.setText(appointments.getAptDescription());
        this.contactCombo.setValue(appointments.getContactId());
        this.startTimeCombo.setValue(appointments.getAptStartTime().toLocalTime());//---HERREEEEEE OJO---
        this.startDatePicker.setValue(LocalDate.from(appointments.getAptStartTime()));
        this.endDatePicker.setValue(LocalDate.from(appointments.getAptEndTime()));
        this.endTimeCombo.setValue(appointments.getAptEndTime().toLocalTime());
        this.titleTxt.setText(appointments.getAptTitle());
        this.locationTxt.setText(appointments.getAptLocation());
        this.typeTxt.setText(appointments.getAptType());
        this.customerCombo.setValue(appointments.getCustomerId());
        this.userCombo.setValue(appointments.getUserId());



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> appointmentIntervals = FXCollections.observableArrayList();

        //Had it set as LocalDateTime however it kept causing my program to crash
        //so i tried using LocalTime since im only using times for the variables to add on to the combo box
        //and it worked for now. Need to keep an eye out if it causes any issues later on.
        LocalTime startingAppointment = LocalTime.of(8,0);
        LocalTime endingAppointment = LocalTime.of(22,0);

        if (!startingAppointment.equals(0) || !endingAppointment.equals(0)){
            while(startingAppointment.isBefore(endingAppointment)){
                appointmentIntervals.add(String.valueOf(startingAppointment));
                startingAppointment = startingAppointment.plusMinutes(30);
            }
        }

        //--Gets contact names--

        ObservableList<Contacts> contactList = DBContacts.getAllContacts();
        ObservableList<String> contactsNames = FXCollections.observableArrayList();//Says name because the goal was to have the names pop up but couldnt figure it out so opted for id number instead.

        //--Gets customer ID--

        ObservableList<Customer> customerIdList = DBCustomers.getAllCustomers();
        ObservableList<Integer> customerId = FXCollections.observableArrayList();

        //--Gets User ID--

        ObservableList<Users> userIdList = DBUsers.getAllUsers();
        ObservableList<Integer> userId = FXCollections.observableArrayList();

        //"abbreviated" for-loop to add into variables the desired outcome being the info that goes into the targeted combo box.

        contactList.forEach(contacts -> contactsNames.add(String.valueOf(contacts.getContactId())));//lambda (acts as a for loop without having to write it out)--Allows for contact combo box to fill with data.
        customerIdList.forEach(customer -> customerId.add(customer.getCustomerId()));//data for customer id combo box
        userIdList.forEach(users -> userId.add(users.getUserId()));//data for user id list

        startTimeCombo.setItems(appointmentIntervals);
        endTimeCombo.setItems(appointmentIntervals);

        contactCombo.setItems(contactsNames);
        customerCombo.setItems(customerId);
        userCombo.setItems(userId);

        startTimeCombo.setVisibleRowCount(6);
        endTimeCombo.setVisibleRowCount(6);
    }
}
