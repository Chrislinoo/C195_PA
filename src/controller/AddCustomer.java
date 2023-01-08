package controller;

import DBAccess.DBCountries;
import DBAccess.DBDivisions;
import Database.JDBC;
import javafx.beans.value.ObservableValue;
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
import model.Divisions;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
    Parent scene;
    Stage stage;

    @FXML
    private TextField addressTxtField;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Countries> countryCombo;

    @FXML
    private TextField customerIdTxtField;

    @FXML
    private ComboBox<Divisions> divisionCombo;

    @FXML
    private TextField nameTxtField;

    @FXML
    private TextField phoneTxtField;

    @FXML
    private TextField postalTxtField;

    @FXML
    private Button saveBtn;

    @FXML
    void address_input(ActionEvent event) {

    }

    @FXML
    void cancelBtn_action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void customerId_input(ActionEvent event) {

    }

    @FXML
    void name_input(ActionEvent event) {

    }

    @FXML
    void phone_input(ActionEvent event) {

    }

    @FXML
    void postal_input(ActionEvent event) {

    }

    @FXML
    void saveBtn_action(ActionEvent event) {
        try {
            String insertSql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(insertSql);

            int newCustomerId = Integer.parseInt(String.valueOf((int) (Math.random() * 100)));

            ps.setInt(1,newCustomerId);
            ps.setString(2,nameTxtField.getText());
            ps.setString(3,addressTxtField.getText());
            ps.setInt(4, Integer.parseInt(postalTxtField.getText()));
            ps.setString(5, phoneTxtField.getText());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, "admin");
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9,"admin");
            ps.setInt(10, (divisionCombo.getValue().getDivisionId()));

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Countries> countriesObservableList = DBCountries.getAllCountries();
            //ObservableList<String> countries = FXCollections.observableArrayList();

            //countriesObservableList.forEach(countries1 -> countries.add(countries1.getName()));

            countryCombo.setItems(countriesObservableList);

//            Countries countrySelected = countryCombo.getSelectionModel().getSelectedItem();
//            ObservableList<Divisions> divisionsObservableList = DBDivisions.getAllDivisions();
//            ObservableList<String> divisions = FXCollections.observableArrayList();
//
//            divisionsObservableList.forEach(divisions1 -> divisions.add(divisions1.getDivisionName()));

//            ObservableList<String> usDivisions = FXCollections.observableArrayList();
//            ObservableList<String> ukDivisions = FXCollections.observableArrayList();
//            ObservableList<String> canadaDivisions = FXCollections.observableArrayList();
//
//            divisionsObservableList.forEach(divisions -> {if (divisions.getCountryId() == 1){
//
//                usDivisions.add(divisions.getDivisionName());
//
//            } else if (divisions.getCountryId() == 2) {
//
//                ukDivisions.add(divisions.getDivisionName());
//
//            } else if (divisions.getCountryId() == 3) {
//
//                canadaDivisions.add(divisions.getDivisionName());
//
//            }
//
//            });

            } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


//            if (countrySelected.equals("U.S")) {
//
//                ObservableList<Divisions> usDivisions = DBDivisions.codeOneDivisions();
//                ObservableList<String> unitedStates = FXCollections.observableArrayList();
//
//                usDivisions.forEach(divisions -> unitedStates.add(divisions.getDivisionName()));
//
//                divisionCombo.setItems(unitedStates);
//
//            }
        }

    /**
     * Filters the division combo box to the appropriate choices depending on the selected country. The divisionsObservableList
     * is calling the codeOneDivisions method and is taking in the Country ID and matching it to the corresponding list of divisions
     * who share that Country ID value. It is then set to the divisionCombo box.
     * @param actionEvent
     */
    public void onActionCountrySwitch(ActionEvent actionEvent) {
        ObservableList<Divisions> divisionsObservableList = DBDivisions.codeOneDivisions(countryCombo.getValue().getId());

        divisionCombo.setItems(divisionsObservableList);


    }
}
