package DBAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {
    /**
     * Method that returns an observable list named "contactsList". It first makes the query , followed by establishing the connection to the DB which then uses
     * a prepared statement with the sql. Then we loop through the results set, and we build new contact objects from the DB.
     * @return the information desired from the database.
     */
    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contacts contacts = new Contacts(contactId, contactName, email);
                contactsList.add(contacts);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactsList;
    }

    /**
     * This method was created for a simpler way of pulling the contact id from a combo box of
     * contact names. The query selects all the info from the database where contact name is included/matching,
     * and then it returns contact ID.
     * @param contactName
     * @return contact ID
     * @throws SQLException
     */
    public static int matchingContact(String contactName) throws SQLException {
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_Name = ?;";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, contactName);

            ResultSet rs = ps.executeQuery();


            int contact_id = 0;
            while (rs.next()) {
                contact_id = rs.getInt("Contact_ID");
                String contactName1 = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
            }
            return contact_id;

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method takes a contact id and returns the contact name it belongs to. This was made for quality of life because its purpose is
     * to make it easier to populate combo boxes with the desired information, in this case being the contactName.
     * @param contactId
     * @return contactName
     * @throws SQLException
     */
    public static String idToName(int contactId) throws SQLException {
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?;";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);

            ResultSet rs = ps.executeQuery();

            String contactName = "";
            while (rs.next()){
                int contact_id = rs.getInt("Contact_ID");
                contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
            }
            return contactName;

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}



