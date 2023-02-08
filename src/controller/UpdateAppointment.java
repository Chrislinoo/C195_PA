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
import java.util.ResourceBundle;

public class UpdateAppointment implements Initializable {

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

    /**
     * Cancels the appointment update and redirects user to the main appointment screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelBtn_Action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.setTitle("Appointment Screen");

        stage.show();
    }

    /**
     * Updates all the desired information into the database using the UPDATE sql statement. Here also lies the alerts that
     * will set off in case of any empty box or missing information. There's also alerts that will flag you if there's an appointment
     * time that overlaps with another one.
     * @param event
     */
    @FXML
    void saveBtn_Action(ActionEvent event) {

        try {
            //--------------------------------Empty Fields--------------------------------------------------------------------------
            if (descriptionTxt.getText().isEmpty() || titleTxt.getText().isEmpty() || locationTxt.getText().isEmpty() || typeTxt.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Make sure all text fields are filled out");
                alert.showAndWait();
                return;
            }
            if (contactCombo.getValue() == null || startTimeCombo.getValue() == null || endTimeCombo.getValue() == null){
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
            if (customerCombo.getValue() == null || userCombo.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Make sure all drop down fields have a selection");
                alert.showAndWait();
                return;
            }
//-------------------------------Creating LocalDateTime From Combo Boxes------------------------------------------------
            LocalDate startDate = startDatePicker.getValue();
            LocalTime startTime = LocalTime.parse(startTimeCombo.getValue().toString());
            LocalDateTime startDateTime = LocalDateTime.of(startDate,startTime);

            LocalDate endDate = endDatePicker.getValue();
            LocalTime endTime = LocalTime.parse(endTimeCombo.getValue().toString());
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

            int businessOpenDay = DayOfWeek.MONDAY.getValue();
            int businessClosingDay = DayOfWeek.FRIDAY.getValue();

            ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());//system default time zone for the LocalDateTime startDateTime
            ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());//system default time zone for the LocalDateTime endDateTime
            ZonedDateTime estConversionStart = zonedDateTimeStart.withZoneSameInstant(ZoneId.of("America/New_York"));//goes from system default to EST
            ZonedDateTime estConversionEnd = zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("America/New_York"));//goes from system default to EST

            DayOfWeek appointmentStartDayName = estConversionStart.toLocalDate().getDayOfWeek();//Gets day if the week from "startDateTime"
            DayOfWeek appointmentEndDayName = estConversionEnd.toLocalDate().getDayOfWeek();//Gets day if the week from "endDateTime"
            int appointmentStartDayName_intValue = appointmentStartDayName.getValue();//turned day of the week into integer value, so we could compare it to open business day values
            int appointmentEndDayName_intValue = appointmentEndDayName.getValue();//turned day of the week into integer value, so we could compare it to open business day values

            if (appointmentStartDayName_intValue < businessOpenDay || appointmentStartDayName_intValue > businessClosingDay){//Appointment has to be within business days
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Appointment date should be during open business days (Monday-Friday)");
                alert.showAndWait();
                return;
            }

            if (appointmentEndDayName_intValue < businessOpenDay || appointmentEndDayName_intValue > businessClosingDay){//Appointment has to be within business days
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Appointment date should be during open business days (Monday-Friday)");
                alert.showAndWait();
                return;
            }
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
            int appointmentId = Integer.parseInt(aptIdTxt.getText());
            int customer_Id = (int) customerCombo.getValue();//CHANGE


            for (Appointments appointments : appointmentsObservableList){

                int existingAppointmentID = appointments.getAppointmentId();
                int existingCustomer = appointments.getCustomerId();//has to be the same customer ID since youre updating that specific customers appointment.
                LocalDateTime startingTestTime = appointments.getAptStartTime();
                LocalDateTime endingTestTime = appointments.getAptEndTime();

                if ((appointmentId != existingAppointmentID) && (customer_Id == existingCustomer) && (startingTestTime.isAfter(startDateTime) || startingTestTime.isEqual(startDateTime)) && startingTestTime.isBefore(endDateTime)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment time overlaps with another appointment, please change the times to an available slot");
                    alert.showAndWait();
                    return;
                }

                if ((appointmentId != existingAppointmentID) && (customer_Id == existingCustomer) && endingTestTime.isAfter(startDateTime) && (endingTestTime.isBefore(endDateTime) || endingTestTime.isEqual(endDateTime))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment time overlaps with another appointment, please change the times to an available slot");
                    alert.showAndWait();
                    return;
                }

                if ((appointmentId != existingAppointmentID) && (customer_Id == existingCustomer) && (startingTestTime.isBefore(startDateTime) || startingTestTime.isEqual(startDateTime)) && (endingTestTime.isAfter(endDateTime) || endingTestTime.isEqual(endDateTime))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment time overlaps with another appointment, please change the times to an available slot");
                    alert.showAndWait();
                    return;
                }

            }


            String updateSql = "UPDATE appointments SET Appointment_ID = ?, Description = ?, Contact_ID = ?, Start = ?, End = ?, Title = ?, Location = ?, Type = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(updateSql);



            ps.setInt(1, Integer.parseInt(aptIdTxt.getText()));
            ps.setString(2, descriptionTxt.getText());
//            ps.setInt(3, Integer.parseInt(contactCombo.getValue().toString()));
            ps.setInt(3, DBContacts.matchingContact(String.valueOf(contactCombo.getValue())));
            ps.setTimestamp(4, Timestamp.valueOf(startDateTime));//--FIXED
            ps.setTimestamp(5, Timestamp.valueOf(endDateTime));//--FIXED
            ps.setString(6, titleTxt.getText());
            ps.setString(7, locationTxt.getText());
            ps.setString(8, typeTxt.getText());
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10, "admin");
            ps.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(12, Integer.parseInt(userCombo.getValue().toString()));
            ps.setInt(13, Integer.parseInt(customerCombo.getValue().toString()));
            ps.setInt(14, Integer.parseInt(userCombo.getValue().toString()));
            ps.setInt(15, Integer.parseInt(aptIdTxt.getText()));

            ps.execute();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method created to be able to load the appointment information to their designated text fields/combo boxes.
     * @param index
     * @param appointments
     */
    public void appointmentTransfer(int index, Appointments appointments) throws SQLException {
        selectedAppointment = appointments;
        selectedIndex = index;

        this.aptIdTxt.setText(String.valueOf(appointments.getAppointmentId()));
        this.descriptionTxt.setText(appointments.getAptDescription());
//        this.contactCombo.setValue(appointments.getContactId());
        this.contactCombo.setValue(DBContacts.idToName(appointments.getContactId()));
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


    /**
     * Initializes the combo boxes needed for time and contact, user, and customer information. Using a for-loop to add
     * into observable array lists and then using that to populate the combo boxes.
      * @param url
     * @param resourceBundle
     */
    @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            ObservableList<String> appointmentIntervals = FXCollections.observableArrayList();

            //Had it set as LocalDateTime however it kept causing my program to crash
            //so i tried using LocalTime since im only using times for the variables to add on to the combo box
            //and it worked for now. Need to keep an eye out if it causes any issues later on.
            LocalTime startingAppointment = LocalTime.of(8, 0);
            LocalTime endingAppointment = LocalTime.of(22, 0);

            if (!startingAppointment.equals(0) || !endingAppointment.equals(0)) {
                while (startingAppointment.isBefore(endingAppointment)) {
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

//        contactList.forEach(contacts -> contactsNames.add(String.valueOf(contacts.getContactId())));//lambda (acts as a for loop without having to write it out)--Allows for contact combo box to fill with data.
            contactList.forEach(contacts -> contactsNames.add(String.valueOf(contacts.getContactName())));//lambda (acts as a for loop without having to write it out)--Allows for contact combo box to fill with data.
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

