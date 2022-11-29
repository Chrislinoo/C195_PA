package main;

import DBAccess.DBCountries;
import Database.JDBC;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Countries;

import java.util.Locale;


/**
 * The Main in which the program will start. It allows for the login screen to be the start of the application.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage)   throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * The beginning of the program. It starts off here by opening the connection to the database
     * and then calls launch. At the end it closes the connection.
     * @param args
     */
    public static void main(String[] args) {

        JDBC.openConnection();
        //--Use this line of code to test the language requirement for the login page-- Locale.setDefault(new Locale("fr"));

        launch(args);



        JDBC.closeConnection();

    }
}