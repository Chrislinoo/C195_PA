package DBAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {

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

}
