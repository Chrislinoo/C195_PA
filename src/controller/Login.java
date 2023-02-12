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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * This is the controller for the login.fxml file. It's responsible for all the elements in the screen.
 */
public class Login implements Initializable {

    Stage stage;
    Parent scene;


    /**
     * Where the application starts. Lets the user know the time zone and if the users region is in France then it translates the
     * page and everything on it to French.
     * @param url
     * @param resourceBundle
     */
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

    /**
     * Logs the user in by checking if username and passwords are a match. The logger is also written here to log successful
     * and unsuccessful logins. Upon logging in you will also get a warning if there's an appointment within 15 minutes or not.
     * @param event
     * @throws IOException
     */
    @FXML
    public void onActionLogIn(ActionEvent event) throws IOException {

        ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/Nat", Locale.getDefault());//Language locator
        //------Logger-----------------------------------------------------------------------------------------------
        String filename = "login_activity.txt";

        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);



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

//                System.out.println(rb.getString("Login Successful"));//Confirms Login
                outputFile.print("Successful login by: " +userIdTxt.getText()+ " at time: " + Timestamp.valueOf(LocalDateTime.now())+ ".");//Confirmation gets written into the file
                outputFile.println("...");

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
                outputFile.print("Unsuccessful login by: " +userIdTxt.getText()+ " at time: " + Timestamp.valueOf(LocalDateTime.now())+ ".");//Fixed, was logging label instead of text box!
                outputFile.println("...");

            }
            outputFile.close();//closes print writer
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
