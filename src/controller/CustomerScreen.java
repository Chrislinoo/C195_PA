package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerScreen {
    Parent scene;
    Stage stage;
    @FXML
    private Button addCustomerBtn;

    @FXML
    private TableColumn<?, ?> address_column;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private TableColumn<?, ?> createdBy_column;

    @FXML
    private TableColumn<?, ?> customerId_column;

    @FXML
    private TableColumn<?, ?> customerName_column;

    @FXML
    private TableColumn<?, ?> dateCreated_column;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private Button exitCustomerBtn;

    @FXML
    private TableColumn<?, ?> lastUpdate_column;

    @FXML
    private TableColumn<?, ?> lastUpdatedBy_column;

    @FXML
    private TableColumn<?, ?> phone_column;

    @FXML
    private TableColumn<?, ?> postalCode_column;

    @FXML
    private Button reportsBtn;

    @FXML
    private TableColumn<?, ?> state_column;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private Button updateCustomerBtn;

    @FXML
    private SplitMenuButton viewBySplit;

    @FXML
    void addBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void appointmentsBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void deleteBtnAction(ActionEvent event) {

    }

    @FXML
    void exitBtnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void reportsBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/reportsContact.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();

    }

    @FXML
    void splitMenuAll(ActionEvent event) {

    }

    @FXML
    void splitMenuMonth(ActionEvent event) {

    }

    @FXML
    void splitMenuWeek(ActionEvent event) {

    }

    @FXML
    void updateBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/updateCustomer.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();

    }

}
