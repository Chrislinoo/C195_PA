package controller;

import DBAccess.DBAppointments;
import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
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
import model.Countries;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Login implements Initializable {

    Stage stage;
    Parent scene;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
//------------------------------Sets zone ID----------------------------------------------------------------------------
            ZoneId zoneId = ZoneId.systemDefault();
            zoneIdLabel.setText(String.valueOf(zoneId));//Sets the zone ID to the label.

//--------Flexes between english and French depending on the users language settings using the resource files.----------
            ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/Nat", Locale.getDefault());

            loginBtn.setText(rb.getString("Login"));
            userIdLabel.setText(rb.getString("UserID"));
            passLabel.setText(rb.getString("Password"));
            credentialsLabel.setText(rb.getString("Credentials"));
            locationLabel.setText(rb.getString("Location"));
            //Flexes between english and French depending on the users language settings using the resource files.










        } catch (MissingResourceException e){
            System.out.println("Missing resource file: " + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private Label credentialsLabel;

    @FXML
    private SplitMenuButton languageSplitMenu;

    @FXML
    private Button loginBtn;

    @FXML
    private Label locationLabel;

    @FXML
    private MenuItem menuItemEng;

    @FXML
    private MenuItem menuItemFrench;

    @FXML
    private Label passLabel;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Label userIdLabel;

    @FXML
    private TextField userIdTxt;

    @FXML
    private Label zoneIdLabel;

    @FXML
    void onActionLogIn(ActionEvent event) throws IOException {

        ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/Nat", Locale.getDefault());

        try {


            String username = userIdTxt.getText();
            String password = passwordTxt.getText();

            ////----------------------------------15 Minute Warnings----------------------------------------------------------------
            ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();

            LocalDateTime presentMinus15 = LocalDateTime.now().minusMinutes(15);
            LocalDateTime presentPlus15 = LocalDateTime.now().plusMinutes(15);
            LocalDateTime timeStarted;
            LocalDateTime localDateTimeFlex = null;
            int appointmentID = 0;
            boolean window15 = false;

            int userID = DBUsers.loginMatch(username, password);

            if (userID > 0) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

                scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

                stage.setScene(new Scene(scene));

                stage.setTitle("Appointment Screen");

                stage.show();

                System.out.println(rb.getString("LoginSuccessful"));
//----------------------------------------------------Logic for the 15 min window---------------------------------------
                for (Appointments appointments : appointmentsObservableList){
                    timeStarted = appointments.getAptStartTime();
                    if (timeStarted.isAfter(presentMinus15) || timeStarted.isEqual(presentMinus15) && timeStarted.isBefore(presentPlus15) || timeStarted.isEqual(presentPlus15)){
                        appointmentID = appointments.getAppointmentId();
                        localDateTimeFlex = timeStarted;
                        window15 = true;
                    }
                }
                if ( window15 != false){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment ID: " +appointmentID+ " has a start time of " +localDateTimeFlex+ ".");
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    System.out.println("Appointment within 15 minutes!");
                }else {Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"No appointments scheduled for the next 15 minutes");
                    Optional<ButtonType> buttonType = alert.showAndWait();
                }
//----------------------------------------------------------------------------------------------------------------------
            }
            else if (userID < 1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(rb.getString("Wrong"));
                alert.showAndWait();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void passwordInput(ActionEvent event) {

    }

    @FXML
    void switchEng(ActionEvent event) {

    }

    @FXML
    void switchFrench(ActionEvent event) {

    }

    @FXML
    void userIdInput(ActionEvent event) {

    }



}
