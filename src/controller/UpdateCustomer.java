package controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customer;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateCustomer implements Initializable {

    Parent scene;
    Stage stage;

    public ComboBox countryCombo;
//    public ComboBox divisionCombo;

//    @FXML
//    private ComboBox<Countries> countryCombo;
//
    @FXML
    private ComboBox<Divisions> divisionCombo;
    @FXML
    private TextField addressTxt;


    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;

    private Customer selectedCustomer;
    private Countries lenderCountry;
    private int selectedIndex;

    private Divisions filteredDivision;

    private Countries selectedCountry;

    @FXML
    void cancelBtn_Action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }


    @FXML
    void divisionCombo_Action(ActionEvent event) {

    }

    @FXML
    void saveBtn_Action(ActionEvent event) {


        try {

            String updateSql = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(updateSql);

            ps.setInt(1, Integer.parseInt(customerIdTxt.getText()));
            ps.setString(2, nameTxt.getText());
            ps.setString(3, addressTxt.getText());
            ps.setInt(4, Integer.parseInt(postalTxt.getText()));
            ps.setString(5, (phoneTxt.getText()));
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7,"admin");
            ps.setTimestamp(8,Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9,"admin");
//            ps.setInt(10, Integer.parseInt(divisionCombo.getSelectionModel().getSelectedItem().toString()));
//            ps.setInt(10, Integer.parseInt(divisionCombo.getValue().toString()));
            ps.setInt(10, (divisionCombo.getValue().getDivisionId()));

            ps.setInt(11,Integer.parseInt(customerIdTxt.getText()));

            ps.execute();

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();



        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void customerTransfer(int index, Customer customer){
        selectedCustomer = customer;
        selectedIndex = index;

        String test ;
        ObservableList<Divisions> wombo = FXCollections.observableArrayList();
        ObservableList<Divisions> divisionsObservable = DBDivisions.codeOneDivisions(customer.getCustomerId());




        this.customerIdTxt.setText(String.valueOf(customer.getCustomerId()));
        this.nameTxt.setText(customer.getCustomerName());
        this.phoneTxt.setText(customer.getCustomerPhone());
        this.addressTxt.setText(customer.getCustomerAddress());
        this.postalTxt.setText(customer.getCustomerPostal());
        this.countryCombo.setValue(customer.getCountryId());
        //this.divisionCombo.setValue(customer.getDivisionName());---FIX THIS AT SOME POINT---

        if (customer.getCountryId() == 1){
            countryCombo.setValue("U.S");
        }
        else if (customer.getCountryId() == 2) {
            countryCombo.setValue("UK");
        }
        else if (customer.getCountryId() == 3) {
            countryCombo.setValue("Canada");
        }

        ObservableList<Divisions> divisionsObservableList = DBDivisions.codeOneDivisions(customer.getCountryId());

        divisionCombo.setItems(divisionsObservableList);
    }

//    public void customerTransfer(int index, Customer customer){
//        selectedCustomer = customer;
//        selectedIndex = index;
//
//        this.customerIdTxt.setText(String.valueOf(customer.getCustomerId()));
//        this.nameTxt.setText(customer.getCustomerName());
//        this.phoneTxt.setText(customer.getCustomerPhone());
//        this.addressTxt.setText(customer.getCustomerAddress());
//        this.postalTxt.setText(customer.getCustomerPostal());
//        this.countryCombo.setValue(customer.getCountryId());
//        this.divisionCombo.setValue(customer.getDivisionName());
//
//
//        if (customer.getCountryId() == 1){
//            countryCombo.setValue("U.S");
//        }
//        else if (customer.getCountryId() == 2) {
//            countryCombo.setValue("UK");
//        }
//        else if (customer.getCountryId() == 3) {
//            countryCombo.setValue("Canada");
//        }
//
//        ObservableList<Divisions> divisionsObservableList = DBDivisions.codeOneDivisions(customer.getCountryId());
//
//        divisionCombo.setItems(divisionsObservableList);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Countries> countriesObservableList = DBCountries.getAllCountries();

            countryCombo.setItems(countriesObservableList);



        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }



    }

    public void countryCombo_Action(ActionEvent actionEvent){
        Countries countrySelected = (Countries) countryCombo.getSelectionModel().getSelectedItem();
        ObservableList<Divisions> dbDivisionsObservableList = DBDivisions.codeOneDivisions(countrySelected.getId());

        divisionCombo.setItems(dbDivisionsObservableList);

    }

//    public void countryCombo_Action(ActionEvent actionEvent) {
//        Countries countrySelected = (Countries) countryCombo.getSelectionModel().getSelectedItem();
//        ObservableList<Divisions> dbDivisionsObservableList = DBDivisions.getAllDivisions();
//
//        ObservableList<String> usDivisions = FXCollections.observableArrayList();
//        ObservableList<String> ukDivisions = FXCollections.observableArrayList();
//        ObservableList<String> canadaDivisions = FXCollections.observableArrayList();
//
//        dbDivisionsObservableList.forEach(divisions -> {if (divisions.getCountryId() == 1){
//            usDivisions.add(divisions.getDivisionName());}
//            else if (divisions.getCountryId() == 2) {
//            ukDivisions.add(divisions.getDivisionName());
//            }
//            else if (divisions.getCountryId() == 3) {
//            canadaDivisions.add(divisions.getDivisionName());
//            }
//        });
//
//        if (countrySelected.equals("U.S")){
//            divisionCombo.setItems(usDivisions);
//        } else if (countrySelected.equals("UK")) {
//            divisionCombo.setItems(ukDivisions);
//        } else if (countrySelected.equals("Canada")) {
//            divisionCombo.setItems(canadaDivisions);
//        }


    }

