package controller;

import Database.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UpdateCustomer {

    Parent scene;
    Stage stage;

    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<?> countryCombo;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private ComboBox<?> divisionCombo;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;

    @FXML
    void cancelBtn_Action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/customerScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void countryCombo_Action(ActionEvent event) {

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
            ps.setInt(5, Integer.parseInt(phoneTxt.getText()));
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7,"admin");
            ps.setTimestamp(8,Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9,"admin");
            ps.setInt(10, Integer.parseInt(divisionCombo.getSelectionModel().getSelectedItem().toString()));
            ps.setInt(11,Integer.parseInt(customerIdTxt.getId()));

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

    }

}
