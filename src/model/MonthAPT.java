package model;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;

public class MonthAPT {
    private String monthName;
    private int totalCount;

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getMonthName() {
        return monthName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public MonthAPT(String monthName/*, int totalCount*/) {
        this.monthName = monthName;
//        this.totalCount = totalCount;
    }

    public static ObservableList<MonthAPT> filteredMonths(String aptType){
        ObservableList<Month> filteredMonths = FXCollections.observableArrayList();
        ObservableList<MonthAPT> appointmentsList = FXCollections.observableArrayList();


        try {
//            String sql = "SELECT * FROM appointments WHERE Type = ?;";
            String sql = "Select monthname(Start) FROM appointments WHERE Type = ?;";


            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, aptType);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
//                int appointmentId = rs.getInt("Appointment_ID");
//                String aptTitle = rs.getString("Title");
//                String aptDescription = rs.getString("Description");
//                String aptLocation = rs.getString("Location");
//                String aptType1 = rs.getString("Type");
                String aptStartTime = rs.getString("monthname(Start)");//Test to see if this works, last method did not.--Prof. said this good! Test with the VM though
//                LocalDateTime aptStartTime = rs.getTimestamp("Start").toLocalDateTime();//Test to see if this works, last method did not.--Prof. said this good! Test with the VM though

//
//                LocalDateTime aptEndTime = rs.getTimestamp("End").toLocalDateTime();
//                int customerId = rs.getInt("Customer_ID");
//                int userId = rs.getInt("User_ID");
//                int contactId = rs.getInt("Contact_ID");

//                Appointments appointment = new Appointments(appointmentId, aptTitle, aptDescription, aptLocation, aptType, aptStartTime, aptEndTime, customerId, userId, contactId);
//                appointmentsList.add(appointment);

                MonthAPT monthAPT = new MonthAPT(aptStartTime);
                appointmentsList.add(monthAPT);
            }

//            appointmentsList.stream().map(appointments -> {
//                return appointments;
//            }).forEach(filteredMonths::add);

        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentsList;
    }

    @Override
    public String toString() {
        return monthName;
    }
}
