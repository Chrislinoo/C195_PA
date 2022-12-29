package controller;

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
import model.Contacts;
import model.Customer;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {

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
    private TextField titleTxtField;

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
    void saveBtn_action(ActionEvent event) throws SQLException {
        try {
            String insertSql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(insertSql);

            int newAppointmentId = Integer.parseInt(String.valueOf((int) (Math.random() * 100)));

            LocalDate startDate = startDatePicker.getValue();
            LocalTime startTime = LocalTime.parse(startAptCombo.getValue());//--TRANSFER THESE LINES OF CODE TO ADD CUSTOMER AS WELL--
            LocalDateTime startDateTime = LocalDateTime.of(startDate,startTime);

            ps.setInt(1, newAppointmentId);
            ps.setString(2, titleTxtField.getText());
            ps.setString(3, descriptionTxtField.getText());
            ps.setString(4, locationTxtField.getText());
            ps.setString(5, typeTxtField.getText());
            ps.setTimestamp(6, Timestamp.valueOf(startDateTime));//Figure this out--Fixed
            ps.setTimestamp(7, Timestamp.valueOf(endDatePicker.getValue().atStartOfDay()));//Figure this out--Fixed
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, "admin");
            ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(11, 1);
            //ps.setInt(12,Integer.parseInt(customerIdTxtField.getText()));original
            ps.setInt(12, Integer.parseInt(customerIdCombo.getValue().toString()));
            //ps.setInt(13,Integer.parseInt(userIdTxtField.getText()));original
            ps.setInt(13, Integer.parseInt(userIdCombo.getValue().toString()));
            //ps.setInt(14,Integer.parseInt(DBContacts.getAllContacts().toString()));original
            ps.setString(14, addContactCombo.getValue());

            ps.execute();

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        } catch (SQLException | NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }

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

    @FXML
    private ComboBox<String> addContactCombo;
    @FXML
    private ComboBox<String> endAptCombo;
    @FXML
    private ComboBox<String> startAptCombo;
    @FXML
    private ComboBox<Integer> customerIdCombo;
    @FXML
    private ComboBox<Integer> userIdCombo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> appointmentIntervals = FXCollections.observableArrayList();

        //Had it set as LocalDateTime however it kept causing my program to crash
        //so i tried using LocalTime since im only using times for the variables to add on to the combo box
        //and it worked for now. Need to keep an eye out if it causes any issues later on.
        LocalTime startingAppointment = LocalTime.from(LocalDateTime.MIN.plusHours(8));
        LocalTime endingAppointment = LocalTime.from(LocalDateTime.MAX.minusHours(1).minusMinutes(30));

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

        startAptCombo.setItems(appointmentIntervals);
        endAptCombo.setItems(appointmentIntervals);

        addContactCombo.setItems(contactsNames);
        customerIdCombo.setItems(customerId);
        userIdCombo.setItems(userId);

        startAptCombo.setVisibleRowCount(6);
        endAptCombo.setVisibleRowCount(6);



    }
}
