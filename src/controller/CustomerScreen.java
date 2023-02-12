package controller;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import Database.JDBC;
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
import model.Appointments;
import model.Customer;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller for the customerScreen.fxml file. It's responsible for all the elements in the screen.
 */
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

    /**
     * Redirects user to the add customer screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void addBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    /**
     * Redirects user to the appointments screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void appointmentsBtnAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    /**
     * Deletes customer from the database. Will issue an alert letting the user know that along with the customer, all appointments
     * tied to said customer will also be deleted.
     * @param event
     */
    @FXML
    public void deleteBtnAction(ActionEvent event) {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Continuing will delete all the customers appointments followed by the customer, do you wish to continue?");
            alert.setTitle("Delete");
            Optional<ButtonType> result = alert.showAndWait();

            Connection connection = JDBC.connection;
            int selectedCustomer = customerTable.getSelectionModel().getSelectedItem().getCustomerId();

            if (result.isPresent() && result.get() == ButtonType.OK){
            DBCustomers.delete(selectedCustomer);
            ObservableList<Customer> customerObservableList = DBCustomers.getAllCustomers();
            customerTable.setItems(customerObservableList);
            }

        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a customer first");
            alert.showAndWait();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Closes the application.
     * @param event
     */
    @FXML
    public void exitBtnAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Redirects user to the Reports page.
     * @param event
     * @throws IOException
     */
    @FXML
    public void reportsBtnAction(ActionEvent event) throws IOException {
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

    /**
     * Redirects user to the update customer screen. In addition to this , it grabs the information of the selected customer
     * and pulls his information to populate the combo boxes so that the user can see the customers already existing data.
     * @param event
     * @throws IOException
     */
    @FXML
    public void updateBtnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateCustomer.fxml"));
            loader.load();

            UpdateCustomer updateCustomer = loader.getController();
            updateCustomer.customerTransfer(customerTable.getSelectionModel().getSelectedIndex(),customerTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a customer first");
            alert.showAndWait();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Populates the table view with the customer objects.
     * @param url
     * @param resourceBundle
     */
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
