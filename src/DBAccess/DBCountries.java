package DBAccess;

import Database.JDBC;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountries extends Countries{

    //made it extend countries model so that i can create the super constructor for it.
    public DBCountries(int id, String name) {
        super(id, name);
    }

    /**
     * Method that returns an observable list named "countriesList". It first makes the query , followed by establishing the connection to the DB which then uses
     * a prepared statement with the sql. Then we loop through the results set, and we build new countries objects from the DB.
     * @return
     */
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                countriesList.add(C);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countriesList;
    }
}
