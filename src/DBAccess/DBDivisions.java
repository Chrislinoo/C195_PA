package DBAccess;

import Database.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class where most queries to the database are performed to be called on throughout the project.
 */
public class DBDivisions {

    /**
     * Method that returns an observable list named "divisionsList". It first makes the query , followed by establishing the connection to the DB which then uses
     * a prepared statement with the sql. Then we loop through the results set, and we build new division objects from the DB.
     * @return the information desired from the database.
     */
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

    /**
     * Created a new SELECT statement to be able to filter divisions depending on country selected. codeOneDivisions takes
     * in an integer parameter. When this method is called its target response is to assign the combo box with the divisions
     * whose Country ID matches.
     * @param countryLabel
     * @return filteredDivisions
     */
    public static ObservableList<Divisions> codeOneDivisions(int countryLabel){
        ObservableList<Divisions> filteredDivisions = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID=?;";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1,countryLabel);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Divisions divisionOne = new Divisions(divisionId, divisionName, countryId);
                filteredDivisions.add(divisionOne);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return filteredDivisions;
    }

    /**
     * Created this method to get the division name from its corresponding ID. This made it easier fixing a problem I had
     * while updating a customers in the database. It populates the combo box with the correct information.
     * @param divisionId
     * @return division
     * @throws SQLException
     */
    public static String divId_divName(int divisionId) throws SQLException {
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);

            preparedStatement.setInt(1, divisionId);
            ResultSet resultSet = preparedStatement.executeQuery();

            String division = "";
            while (resultSet.next()) {
                int division_id = resultSet.getInt("Division_ID");
                division = resultSet.getString("Division");
                int countryId = resultSet.getInt("Country_ID");
            }
            return division;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
