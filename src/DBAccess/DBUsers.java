package DBAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {

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

}
