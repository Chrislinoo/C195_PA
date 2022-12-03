package DBAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBAppointments {

    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String aptTitle = rs.getString("Title");
                String aptDescription = rs.getString("Description");
                String aptLocation = rs.getString("Location");
                String aptType = rs.getString("Type");
                LocalDateTime aptStartTime = rs.getTimestamp("Start").toLocalDateTime();//Test to see if this works, last method did not.
                LocalDateTime aptEndTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointment = new Appointments(appointmentId, aptTitle, aptDescription, aptLocation, aptType, aptStartTime, aptEndTime, customerId, userId, contactId);
                appointmentsList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsList;
    }

}
