package controller;

import DBAccess.DBCountries;
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
import javafx.scene.control.Alert;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateCustomer implements Initializable {

    Parent scene;
    Stage stage;

    public ComboBox<Countries> countryCombo;
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

    /**
     * Updates the customer in the database. Also sets off alerts if anything isnt right while updating the fields.
     * @param event
     */
    @FXML
    void saveBtn_Action(ActionEvent event) {//FIX DIVISION COMBO --ASK PROFESSOR--


        try {

            if (addressTxt.getText().isEmpty() || postalTxt.getText().isEmpty() || nameTxt.getText().isEmpty() || phoneTxt.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please make sure all text fields are filled out correctly");
                alert.showAndWait();
                return;
            }
            if (countryCombo.getValue() == null || divisionCombo.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Make sure all drop down fields have a selection");
                alert.showAndWait();
                return;
            }

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

    /**
     * Populates the existing customers information into the text fields when updating.
     * @param index
     * @param customer
     * @throws SQLException
     */
    public void customerTransfer(int index, Customer customer) throws SQLException {
        selectedCustomer = customer;
        selectedIndex = index;
        int tester = customer.getDivisionId();

        ObservableList<Divisions> wombo = FXCollections.observableArrayList();
        String divisionsObservable = DBDivisions.divId_divName(customer.getDivisionId());




        this.customerIdTxt.setText(String.valueOf(customer.getCustomerId()));
        this.nameTxt.setText(customer.getCustomerName());
        this.phoneTxt.setText(customer.getCustomerPhone());
        this.addressTxt.setText(customer.getCustomerAddress());
        this.postalTxt.setText(customer.getCustomerPostal());
//        this.countryCombo.setValue(customer.getCountryId());
        //this.divisionCombo.setPromptText(divisionsObservable);
        //this.divisionCombo.setValue(customer.getDivisionName());

        ObservableList<Divisions> divisionsObservableList = DBDivisions.codeOneDivisions(customer.getCountryId());

        divisionCombo.setItems(divisionsObservableList);
        System.out.println(divisionsObservable);//To see what value is pulled.

        for (Countries c : countryCombo.getItems()){
            if (c.getId() == customer.getCountryId()){
                countryCombo.setValue(c);
            }
        }

        for (Divisions d : divisionCombo.getItems()){
            if (d.getDivisionId() == customer.getDivisionId()){
                divisionCombo.setValue(d);
            }
        }


    }


    /**
     * Populates the combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ObservableList<Countries> countriesObservableList = DBCountries.getAllCountries();

            countryCombo.setItems(countriesObservableList);



        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }



    }

    /**
     * Upon selecting a country in the combo box it populates the division combo box accordingly.
     * @param actionEvent
     */
    public void countryCombo_Action(ActionEvent actionEvent){
        Countries countrySelected = (Countries) countryCombo.getSelectionModel().getSelectedItem();
        ObservableList<Divisions> dbDivisionsObservableList = DBDivisions.codeOneDivisions(countrySelected.getId());

        divisionCombo.setItems(dbDivisionsObservableList);

    }

}

