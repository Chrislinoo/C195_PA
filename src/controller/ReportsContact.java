package controller;

        import DBAccess.DBAppointments;
        import DBAccess.DBContacts;
        import DBAccess.DBCustomers;
        import Database.JDBC;
        import javafx.collections.FXCollections;
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
        import model.Contacts;
        import model.MonthAPT;
        import model.TypeAPT;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.time.LocalDateTime;
        import java.time.Month;
        import java.util.Collections;
        import java.util.ResourceBundle;


public class ReportsContact implements Initializable {
    Parent scene;
    Stage stage;
    @FXML
    private Button totalGeneratorButton;
    @FXML
    private TableColumn<?, ?> appointmentPerMonthColumn;

    @FXML
    private TableColumn<?, ?> appointmentPerMonthColumn_Total;
    @FXML
    private TableColumn<?, ?> appointmentTypeColumn;

    @FXML
    private TableColumn<?, ?> appointmentTypeColumn_Total;


    @FXML
    private TableColumn<Appointments, Integer> appointmentId_column;

    @FXML
    private TableColumn<Appointments, Integer> customerId_column;

    @FXML
    private TableColumn<Appointments, String> description_column;

    @FXML
    private Button exitBtn;

    @FXML
    private ComboBox<String> filterCombo;

    @FXML
    private TableColumn<Appointments, LocalDateTime> timeFinished_column;

    @FXML
    private TableColumn<Appointments, LocalDateTime> timeStart_column;

    @FXML
    private TableColumn<Appointments, String> title_column;

    @FXML
    private TableView<Appointments> contactsTableView;
    @FXML
    private TableView<MonthAPT> aptMonthMini;

    @FXML
    private TableView<TypeAPT> aptTypeMini;

    @FXML
    private Label generatedTotalLabel;


    @FXML
    private TableColumn<Appointments, String> type_column;


    @FXML
    void exitBtn_action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void filterCombo_action(ActionEvent event) {
        try {

            ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();
            ObservableList<Contacts> contactsObservableList = DBContacts.getAllContacts();
            ObservableList<Appointments> filteredAptList = FXCollections.observableArrayList();
            int contactId = 0;


            Appointments desiredAptInfo;
            String contactName = filterCombo.getSelectionModel().getSelectedItem();

            for (Contacts contacts : contactsObservableList) {
                if (contactName.equals(contacts.getContactName())) {
                    contactId = contacts.getContactId();
                }
            }
            for (Appointments appointments : appointmentsObservableList) {
                if (appointments.getContactId() == contactId) {
                    desiredAptInfo = appointments;
                    filteredAptList.add(desiredAptInfo);
                }
            }
            contactsTableView.setItems(filteredAptList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //-----------------------------------------------------------------------------------

        ObservableList<Appointments> appointments = DBAppointments.getAllAppointments();
        contactsTableView.setItems(DBAppointments.getAllAppointments());

        appointmentId_column.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title_column.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        description_column.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        type_column.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        timeStart_column.setCellValueFactory(new PropertyValueFactory<>("aptStartTime"));
        timeFinished_column.setCellValueFactory(new PropertyValueFactory<>("aptEndTime"));
        customerId_column.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        contactsTableView.setItems(appointments);

        //-----------------------------------------------------------------------------------

        ObservableList<Contacts> contactsList = DBContacts.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        contactsList.forEach(contacts -> contactNames.add(contacts.getContactName()));
        filterCombo.setItems(contactNames);

        //-----------------------------------------------------------------------------------
        ObservableList<Month> monthObservableList = FXCollections.observableArrayList();
        ObservableList<Month> monthsApt = FXCollections.observableArrayList();
        ObservableList<MonthAPT> reportPerMonth = FXCollections.observableArrayList();

        appointments.stream().map(appointments1 -> {
            return appointments1.getAptStartTime().getMonth();
        }).forEach(monthObservableList::add);

        monthObservableList.stream().filter(month -> {
            return !monthsApt.contains(month);
        }).forEach(monthsApt::add);

        for (Month month : monthsApt) {
            int totalCount = Collections.frequency(monthObservableList, month);
            String monthName = month.name();
            MonthAPT monthAPT = new MonthAPT(monthName, totalCount);
            reportPerMonth.add(monthAPT);
        }
        aptMonthMini.setItems(reportPerMonth);
        appointmentPerMonthColumn.setCellValueFactory(new PropertyValueFactory<>("monthName"));
        appointmentPerMonthColumn_Total.setCellValueFactory(new PropertyValueFactory<>("totalCount"));

        //-----------------------------------------------------------------------------------

        ObservableList<String> typeObservableList = FXCollections.observableArrayList();
        ObservableList<String> special = FXCollections.observableArrayList();
        ObservableList<TypeAPT> typePerMonth = FXCollections.observableArrayList();

        appointments.forEach(appointments1 -> {
            typeObservableList.add(appointments1.getAptType());
        });

        for (Appointments appointments1 : appointments) {
            String appointmentsAppointmentType = appointments1.getAptType();
            if (!special.contains(appointmentsAppointmentType)) {
                special.add(appointmentsAppointmentType);
            }
        }

        for (String type : special) {
            String typeToSet = type;
            int typeTotal = Collections.frequency(typeObservableList, type);
            TypeAPT typeAPT = new TypeAPT(typeToSet, typeTotal);
            typePerMonth.add(typeAPT);
        }

        aptTypeMini.setItems(typePerMonth);

        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeReport"));
        appointmentTypeColumn_Total.setCellValueFactory(new PropertyValueFactory<>("totalCount"));


    }

    @FXML
    void generateTotal_Action(ActionEvent event) throws SQLException {
        String sql = "SELECT count(*) FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int totalCount = rs.getInt("count(*)");
            generatedTotalLabel.setText(String.valueOf(totalCount));
        }
    }
}

