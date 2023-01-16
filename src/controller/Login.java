package controller;

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
import model.Countries;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class Login implements Initializable {

    Stage stage;
    Parent scene;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zoneId = ZoneId.systemDefault();
        zoneIdLabel.setText(String.valueOf(zoneId));//Sets the zone ID to the label.

    }

    @FXML
    private Label credentialsLabel;

    @FXML
    private SplitMenuButton languageSplitMenu;

    @FXML
    private Button loginBtn;

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
//        ObservableList<Customer> Customers = DBCustomers.getAllCustomers();
//        for (Customer customers : Customers){
//            System.out.println("Customer ID: " + customers.getCustomerId() + " Name: " + customers.getCustomerName());
//        }
        try {
            String username = userIdTxt.getText();
            String password = passwordTxt.getText();

            int userID = DBUsers.loginMatch(username, password);

            if (userID > 0) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

                scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

                stage.setScene(new Scene(scene));

                stage.setTitle("Appointment Screen");

                stage.show();

                System.out.println("Login Successful");
            }
            else if (userID < 1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Incorrect username and password.");
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
