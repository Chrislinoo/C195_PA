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
    /**
     * Method that returns an observable list named "appointmentsList". It first makes the query , followed by establishing the connection to the DB which then uses
     * a prepared statement with the sql. Then we loop through the results set, and we build new appointments objects from the DB.
     * @return the information desired from the database.
     */
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
                LocalDateTime aptStartTime = rs.getTimestamp("Start").toLocalDateTime();//Test to see if this works, last method did not.--Prof. said this good! Test with the VM though
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

    public static int delete(int appointment_Id) throws SQLException {
        String deleteSql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(deleteSql);


        ps.setInt(1,appointment_Id);


        int rowsAffected = ps.executeUpdate();
        ps.close();

        return rowsAffected;
    }

//    public static ObservableList<Month> monthCount() throws SQLException {
//        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
//        String monthSql = "SELECT COUNT(*), monthName(Start) FROM appointments";
//        PreparedStatement ps = JDBC.connection.prepareStatement(monthSql);
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()){
//            LocalDateTime aptStartTime = rs.getTimestamp("Start").toLocalDateTime();//Test to see if this works, last method did not.--Prof. said this good! Test with the VM though
//
//            Month appointment = new Month(aptStartTime);
//            appointments.add(appointment);
//        }
//        return appointments;
//    }
}
