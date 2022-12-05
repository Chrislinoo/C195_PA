package DBAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {

    /**
     * Method that returns an observable list named "usersList". It first makes the query , followed by establishing the connection to the DB which then uses
     * a prepared statement with the sql. Then we loop through the results set, and we build new user objects from the DB.
     * @return the information desired from the database.
     */
    public static ObservableList<Users> getAllUsers(){
        ObservableList<Users> usersList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from users";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                Users user = new Users(userId, userName, userPassword);
                usersList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usersList;
    }

    /**
     * Method to validate the username and password for the login form. loginMatch looks for 2 input strings being username and password and compares it to the columns
     * in the database accordingly. If it goes through without error it will return user id. If an existing user id exists (Which will be any number greater than 0 then
     * it should log in and if a user id that does not exist it will return an error screen.)
     * @param username
     * @param password
     * @return userId
     */
    public static int loginMatch(String username, String password){
        try {
            String sql = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '"+ password +"'";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            rs.next();
            if (rs.getString("User_Name").equals(username))
                if (rs.getString("Password").equals(password))
                    return rs.getInt("User_ID");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

}
