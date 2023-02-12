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
import javafx.scene.control.Alert;
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

/**
 * This is the controller for the addCustomer.fxml file. It's responsible for all the elements in the screen.
 */
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

    /**
     * Cancels adding a customer and returns user to the customer screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void cancelBtn_action(ActionEvent event) throws IOException {
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

    /**
     * Saves customer into the database. Using bind variables to assign what goes where, and we take the values from the
     * text fields and combo boxes.
     * @param event
     */
    @FXML
    public void saveBtn_action(ActionEvent event) {
        try {

            if (addressTxtField.getText().isEmpty() || postalTxtField.getText().isEmpty() || nameTxtField.getText().isEmpty() || phoneTxtField.getText().isEmpty()){
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

    /**
     * Populates the country combo.
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
