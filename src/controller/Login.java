package controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Countries;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    void onActionLogIn(ActionEvent event) {
//        ObservableList<Customer> Customers = DBCustomers.getAllCustomers();
//        for (Customer customers : Customers){
//            System.out.println("Customer ID: " + customers.getCustomerId() + " Name: " + customers.getCustomerName());
//        }

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


    /*public void showMeBtn(javafx.event.ActionEvent actionEvent) {
        ObservableList<Countries> Countries = DBCountries.getAllCountries();
        for (Countries C : Countries){
            System.out.println("Country ID: " + C.getId() + " Name: " + C.getName());
        }
    }*/

}
