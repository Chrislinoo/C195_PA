package controller;

import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerScreen implements Initializable {
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
    private TableView<Customer> customerTable;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customerObservableList = DBCustomers.getAllCustomers();
        customerTable.setItems(DBCustomers.getAllCustomers());

        customerId_column.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName_column.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        address_column.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCode_column.setCellValueFactory(new PropertyValueFactory<>("customerPostal"));
        phone_column.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        state_column.setCellValueFactory(new PropertyValueFactory<>("divisionName"));




        customerTable.setItems(customerObservableList);
    }
}
