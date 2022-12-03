package DBAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDivisions {

    public static ObservableList<Divisions> getAllDivisions(){
        ObservableList<Divisions> divisionsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first-level divisions";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Divisions division = new Divisions(divisionId, divisionName, countryId);
                divisionsList.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionsList;
    }
}
