package controller;

        import DBAccess.DBAppointments;
        import DBAccess.DBContacts;
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
        import java.util.Arrays;
        import java.util.ResourceBundle;


public class ReportsContact implements Initializable {
    public ComboBox<String> appointmentTypeCombo;
    public ComboBox<String> monthCombo;
    public Label typeMonthLabel;
    public TableView typeMonthTable;
    public TableColumn appointmentId_column1;
    public TableColumn title_column1;
    public TableColumn description_column1;
    public TableColumn location_column;
    public TableColumn contact_column;
    public TableColumn type_column1;
    public TableColumn startTime_column;
    public TableColumn endTime_column;
    public TableColumn customerId_column1;
    public TableColumn userId_column;
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

    ObservableList<String> months = FXCollections.observableArrayList(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));


    @FXML
    private TableColumn<Appointments, String> type_column;


    /**
     * Redirects user to the appointments screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void exitBtn_action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    /**
     * Filters the tableview according to the selected contact. Firt created 3 lists. One will have all our appointments, the next will have all our contacts.
     * The third one will be filled with the id that we have selected and then using that list we populate the tableview to display the desired information.
     * @param event
     */
    @FXML
    void filterCombo_action(ActionEvent event) {
        try {

            ObservableList<Appointments> appointmentsObservableList = DBAppointments.getAllAppointments();
            ObservableList<Contacts> contactsObservableList = DBContacts.getAllContacts();
            ObservableList<Appointments> filteredAptList = FXCollections.observableArrayList();
            int contactId = 0;//Initialize


            Appointments desiredAptInfo;
            String contactName = filterCombo.getSelectionModel().getSelectedItem();//Contact name = the combo boxes selected choice.

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

    /**
     * Initializes and populates the graph as well as the combo boxes we will be using.
     * @param url
     * @param resourceBundle
     */
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
        contactsList.forEach(contacts -> contactNames.add(contacts.getContactName()));//adds contact names into list so that we can use it for the combo box that affects filtering by contact.
        filterCombo.setItems(contactNames);

        //-----------------------------------------------------------------------------------

        ObservableList<String> typeObservableList = FXCollections.observableArrayList();
        ObservableList<String> special = FXCollections.observableArrayList();

        appointments.forEach(appointments1 -> {
            typeObservableList.add(appointments1.getAptType());//Adds all appointment types into list
        });

        for (Appointments appointments1 : appointments) {
            String appointmentsAppointmentType = appointments1.getAptType();
            if (!special.contains(appointmentsAppointmentType)) {
                special.add(appointmentsAppointmentType);//adds non repeating values of type into the special list which we will use for the combo
            }
        }

        appointmentTypeCombo.setItems(special);


        monthCombo.setItems(months);







//        ObservableList<Month> monthObservableList = FXCollections.observableArrayList();
//        ObservableList<Month> monthsApt = FXCollections.observableArrayList();
//        ObservableList<MonthAPT> reportPerMonth = FXCollections.observableArrayList();
//
//        appointments.stream().map(appointments1 -> {
//            return appointments1.getAptStartTime().getMonth();
//        }).forEach(monthObservableList::add);
//
//        monthObservableList.stream().filter(month -> {
//            return !monthsApt.contains(month);
//        }).forEach(monthsApt::add);
//
//        for (Month month : monthsApt) {
//            int totalCount = Collections.frequency(monthObservableList, month);
//            String monthName = month.name();
//            MonthAPT monthAPT = new MonthAPT(monthName, totalCount);
//            reportPerMonth.add(monthAPT);
//        }
//
//        monthCombo.setItems(reportPerMonth);
//        aptMonthMini.setItems(reportPerMonth);
//        appointmentPerMonthColumn.setCellValueFactory(new PropertyValueFactory<>("monthName"));
//        appointmentPerMonthColumn_Total.setCellValueFactory(new PropertyValueFactory<>("totalCount"));

        //-----------------------------------------------------------------------------------

//        ObservableList<String> typeObservableList = FXCollections.observableArrayList();
//        ObservableList<String> special = FXCollections.observableArrayList();
//        ObservableList<TypeAPT> typePerMonth = FXCollections.observableArrayList();
//
//        appointments.forEach(appointments1 -> {
//            typeObservableList.add(appointments1.getAptType());
//        });
//
//        for (Appointments appointments1 : appointments) {
//            String appointmentsAppointmentType = appointments1.getAptType();
//            if (!special.contains(appointmentsAppointmentType)) {
//                special.add(appointmentsAppointmentType);
//            }
//        }
//
//        for (String type : special) {
//            String typeToSet = type;
//            int typeTotal = Collections.frequency(typeObservableList, type);
//            TypeAPT typeAPT = new TypeAPT(typeToSet, typeTotal);
//            typePerMonth.add(typeAPT);
//        }
//
//        appointmentTypeCombo.setItems(typeObservableList);
//
//        aptTypeMini.setItems(typePerMonth);
//
//        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeReport"));
//        appointmentTypeColumn_Total.setCellValueFactory(new PropertyValueFactory<>("totalCount"));


    }

    /**
     * The generate total button returns a count of all the registered customers in the database.
     * @param event
     * @throws SQLException
     */
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

    /**
     * Runs a sql query where it searches the database for appointments of selected type and month. Uses the combo box values as input
     * for the bind variables and returns the info into an observable list to be able to populate the tableview. I also have the tableview
     * give me the number of rows to give the user a visual number instead of just a table alone. Throws an error if no appointment is found
     * within the results.
     * @param actionEvent
     */
    public void typeMonth_Generator(ActionEvent actionEvent) {

        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Type = ? AND monthname(Start) = ?;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, appointmentTypeCombo.getValue());
            ps.setString(2, monthCombo.getValue());

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String aptTitle = rs.getString("Title");
                String aptDescription = rs.getString("Description");
                String aptLocation = rs.getString("Location");
                String aptType = rs.getString("Type");
                LocalDateTime aptStartTime = rs.getTimestamp("Start").toLocalDateTime();//Test to see if this works, last method did not.--Prof. said this good! Test with the VM though
                LocalDateTime aptEndTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments = new Appointments(appointmentId, aptTitle, aptDescription, aptLocation, aptType, aptStartTime, aptEndTime, customerId, userId, contactId);
                appointmentsObservableList.add(appointments);
            }
            if (appointmentsObservableList.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("There are no existing " +appointmentTypeCombo.getValue()+ " type appointments for the month of " +monthCombo.getValue()+ ".");
                alert.showAndWait();
            }

//----------------------------------------------------------------------------------------------------------------------

            typeMonthLabel.setText("Total for selected combination is:  " + String.valueOf(appointmentsObservableList.size()));//Sets the total count for the list and sets the label text to give users a visible number of count.


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//------------------Sets the appointment type and month table for users to see------------------------------------------

        typeMonthTable.setItems(appointmentsObservableList);
        appointmentId_column1.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title_column1.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        description_column1.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        location_column.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        contact_column.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type_column1.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        startTime_column.setCellValueFactory(new PropertyValueFactory<>("aptStartTime"));
        endTime_column.setCellValueFactory(new PropertyValueFactory<>("aptEndTime"));
        customerId_column1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId_column.setCellValueFactory(new PropertyValueFactory<>("userId"));
        typeMonthTable.setItems(appointmentsObservableList);

//----------------------------------------------------------------------------------------------------------------------



    }

    public void appointmentTypeCombo_Action(ActionEvent actionEvent) {

    }
}

