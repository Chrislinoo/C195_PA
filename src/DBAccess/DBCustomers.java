package DBAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Method that returns an observable list named "customersList". It first makes the query , followed by establishing the connection to the DB which then uses
 * a prepared statement with the sql variable. Then we loop through the results set, and we build new customer objects from the DB.
 * @return the information desired from the database.
 */
public class DBCustomers {

    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customersList = FXCollections.observableArrayList();

        try {
            //String sql = "SELECT * from customers";
            String sql = "SELECT * FROM customers LEFT JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostal = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Customer customer = new Customer(customerId, customerName, customerAddress, customerPostal, customerPhone, divisionId, divisionName, countryId);
                customersList.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customersList;
    }

    public static int delete(int customer_Id) throws SQLException {
        String deleteSql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(deleteSql);


        ps.setInt(1,customer_Id);


        int rowsAffected = ps.executeUpdate();
        ps.close();

        return rowsAffected;
    }



}
