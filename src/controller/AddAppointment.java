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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customer;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
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

    /**
     * Cancels the addition of adding an appointment to the database and returns user to the main appointment screen.
     * @param event
     * @throws IOException
     */
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

    /**
     * Using bind variables this method inserts appointments into the database. It also contains all the necessary alerts
     * when adding an appointment to the database as well as an alert if theres an overlapping appointment.
     * @param event
     * @throws SQLException
     */
    @FXML
    void saveBtn_action(ActionEvent event) throws SQLException {

        try {
//--------------------------------Empty Fields--------------------------------------------------------------------------
            if (descriptionTxtField.getText().isEmpty() || titleTxtField.getText().isEmpty() || locationTxtField.getText().isEmpty() || typeTxtField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Make sure all text fields are filled out");
                alert.showAndWait();
                return;
            }
            if (addContactCombo.getValue().isEmpty() || startAptCombo.getValue().isEmpty() || endAptCombo.getValue().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Make sure all drop down fields have a selection");
                alert.showAndWait();
                return;
            }
            if (startDatePicker.getValue() == null || endDatePicker.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select an appointment start date");
                alert.showAndWait();
                return;
            }
            if (customerIdCombo.getValue() == null || userIdCombo.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Make sure all drop down fields have a selection");
                alert.showAndWait();
                return;
            }
//-------------------------------Creating LocalDateTime From Combo Boxes------------------------------------------------
            LocalDate startDate = startDatePicker.getValue();
            LocalTime startTime = LocalTime.parse(startAptCombo.getValue());
            LocalDateTime startDateTime = LocalDateTime.of(startDate,startTime);

            LocalDate endDate = endDatePicker.getValue();
            LocalTime endTime = LocalTime.parse(endAptCombo.getValue());
            LocalDateTime endDateTime = LocalDateTime.of(endDate,endTime);

            if (startDate.isAfter(endDate)){//Has to have realistic days for setting an appointment
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Appointment start day should be before its end day");
                alert.showAndWait();
                return;
            }
            if (endDate.isBefore(startDate)){//Has to have realistic days for setting an appointment
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Appointment end day should be after its start day");
                alert.showAndWait();
                return;
            }

//-------------------------------Opening Day Schedule Check-------------------------------------------------------------

//            int businessOpenDay = DayOfWeek.MONDAY.getValue();
//            int businessClosingDay = DayOfWeek.FRIDAY.getValue();

            ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());//system default time zone for the LocalDateTime startDateTime on line 133
            ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());//system default time zone for the LocalDateTime endDateTime on line 137
            ZonedDateTime estConversionStart = zonedDateTimeStart.withZoneSameInstant(ZoneId.of("America/New_York"));//goes from system default to EST
            ZonedDateTime estConversionEnd = zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("America/New_York"));//goes from system default to EST

//            DayOfWeek appointmentStartDayName = estConversionStart.toLocalDate().getDayOfWeek();//Gets day if the week from "startDateTime"
//            DayOfWeek appointmentEndDayName = estConversionEnd.toLocalDate().getDayOfWeek();//Gets day if the week from "endDateTime"
//            int appointmentStartDayName_intValue = appointmentStartDayName.getValue();//turned day of the week into integer value, so we could compare it to open business day values
//            int appointmentEndDayName_intValue = appointmentEndDayName.getValue();//turned day of the week into integer value, so we could compare it to open business day values

//            if (appointmentStartDayName_intValue < businessOpenDay || appointmentStartDayName_intValue > businessClosingDay){//Appointment has to be within business days
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setContentText("Appointment date should be during open business days (Monday-Friday)");
//                alert.showAndWait();
//                return;
//            }

//            if (appointmentEndDayName_intValue < businessOpenDay || appointmentEndDayName_intValue > businessClosingDay){//Appointment has to be within business days
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setContentText("Appointment date should be during open business days (Monday-Friday)");
//                alert.showAndWait();
//                return;
//            }
//--------------------------------Opening Hour Schedule Check-----------------------------------------------------------
            LocalTime verifyAppointmentStartTime = estConversionStart.toLocalTime();//EST to Local
            LocalTime verifyAppointmentEndTime = estConversionEnd.toLocalTime();//EST to Local
            LocalTime businessOpenTime = LocalTime.of(8,00);//Opening Time
            LocalTime businessCloseTime = LocalTime.of(22,00);//Closing Time

            if (verifyAppointmentStartTime.isBefore(businessOpenTime) || verifyAppointmentStartTime.isAfter(businessCloseTime)){//Appointment has to be within business hours
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Appointment start time should be during open business hours (8:00 AM EST - 10:00 PM EST)");
                alert.showAndWait();
                return;
            }

            if (verifyAppointmentEndTime.isBefore(businessOpenTime) || verifyAppointmentEndTime.isAfter(businessCloseTime)){//Appointment has to be within business hours
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Appointment end time should be during open business hours (8:00 AM EST - 10:00 PM EST)");
                alert.showAndWait();
                return;
            }

            if (startTime.isAfter(endTime)){//Has to have logical times
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Start time should be before end time");
                alert.showAndWait();
                return;
            }

            if (endTime.isBefore(startTime)){//Has to have logical times
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("End time should be before start time");
                alert.showAndWait();
                return;
            }

            if (startTime.equals(endTime)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Start and end times cannot be the same");
                alert.showAndWait();
                return;
            }

            if (endTime.equals(startTime)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Start and end times cannot be the same");
                alert.showAndWait();
                return;
            }
//--------------------------Overlapping appointments for Start, End, and existing appointments----See Overlapping
//appointments video for reference......
            // - LocalDateTime methods:
            // - A > B is "A.isAfter(B)"
            // - A == B is "A.isEqual(B)"
            // - A < B is "A.isBefore(B)"
            //Greater than or equal to is a combo of these with an OR
            // - A <= B is (A < B || A == B) ---- (A.isBefore(B) || A.isEqual(B))
            // Needs : -for loop    -LocalDateTime times check to compare the new times with the existing times -Also a
            //statement that makes sure the customer ID is the same while the appointment ID is not equal to an existing one....
            ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();
            int newAppointmentId = Integer.parseInt(String.valueOf((int) (Math.random() * 100)));
            int customer_Id = customerIdCombo.getValue();


            for (Appointments appointments : appointmentsObservableList){

                int existingAppointmentID = appointments.getAppointmentId();
                int existingCustomerID = appointments.getCustomerId();
                LocalDateTime startingTestTime = appointments.getAptStartTime();
                LocalDateTime endingTestTime = appointments.getAptEndTime();

                if ((newAppointmentId != existingAppointmentID) && (customer_Id == existingCustomerID) && (startingTestTime.isAfter(startDateTime) || startingTestTime.isEqual(startDateTime)) && startingTestTime.isBefore(endDateTime)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment time overlaps with another appointment, please change the times to an available slot");
                    alert.showAndWait();
                    return;
                }

                if ((newAppointmentId != existingAppointmentID) && (customer_Id == existingCustomerID) && endingTestTime.isAfter(startDateTime) && (endingTestTime.isBefore(endDateTime) || endingTestTime.isEqual(endDateTime))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment time overlaps with another appointment, please change the times to an available slot");
                    alert.showAndWait();
                    return;
                }

                if ((newAppointmentId != existingAppointmentID) && (customer_Id == existingCustomerID) && (startingTestTime.isBefore(startDateTime) || startingTestTime.isEqual(startDateTime)) && (endingTestTime.isAfter(endDateTime) || endingTestTime.isEqual(endDateTime))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment time overlaps with another appointment, please change the times to an available slot");
                    alert.showAndWait();
                    return;
                }

            }
//------------------------------------------Saving the appointment------------------------------------------------------

            String insertSql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(insertSql);




            ps.setInt(1, newAppointmentId);
            ps.setString(2, titleTxtField.getText());
            ps.setString(3, descriptionTxtField.getText());
            ps.setString(4, locationTxtField.getText());
            ps.setString(5, typeTxtField.getText());
            ps.setTimestamp(6, Timestamp.valueOf(startDateTime));//Figure this out--Fixed
            ps.setTimestamp(7, Timestamp.valueOf(endDateTime));//Figure this out--Fixed
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, "admin");
            ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(11, "admin");
            //ps.setInt(12,Integer.parseInt(customerIdTxtField.getText()));original
            ps.setInt(12, Integer.parseInt(customerIdCombo.getValue().toString()));
            //ps.setInt(13,Integer.parseInt(userIdTxtField.getText()));original
            ps.setInt(13, Integer.parseInt(userIdCombo.getValue().toString()));
            //ps.setInt(14,Integer.parseInt(DBContacts.getAllContacts().toString()));original
//            ps.setString(14, addContactCombo.getValue());
            ps.setString(14, String.valueOf(DBContacts.matchingContact(addContactCombo.getValue())));

            ps.execute();

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }
        catch (SQLException | NumberFormatException | IOException e) {
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


    /**
     * Initializes the combo boxes needed for time and contact, user, and customer information. Using a for-loop to add
     * into observable array lists and then using that to populate the combo boxes. This method uses 3 lambdas to improve the
     * structure of the code.
     * @param url
     * @param resourceBundle
     */
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

//        contactList.forEach(contacts -> contactsNames.add(String.valueOf(contacts.getContactId())));// (acts as a for loop without having to write it out)--Allows for contact combo box to fill with data.
        contactList.forEach(contacts -> contactsNames.add(String.valueOf(contacts.getContactName())));//lambda 1!(acts as a for loop without having to write it out)--Allows for contact combo box to fill with data.
        customerIdList.forEach(customer -> customerId.add(customer.getCustomerId()));//lambda 2! data for customer id combo box
        userIdList.forEach(users -> userId.add(users.getUserId()));//lambda 3! data for user id list

        startAptCombo.setItems(appointmentIntervals);
        endAptCombo.setItems(appointmentIntervals);

        addContactCombo.setItems(contactsNames);
        customerIdCombo.setItems(customerId);
        userIdCombo.setItems(userId);

        startAptCombo.setVisibleRowCount(6);
        endAptCombo.setVisibleRowCount(6);



    }
}
