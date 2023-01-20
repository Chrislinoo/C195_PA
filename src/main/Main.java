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
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * The Main in which the program will start. It allows for the login screen to be the start of the application.
 */
public class Main extends Application {
    ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle/Nat",Locale.getDefault());
    @Override
    public void start(Stage primaryStage)   throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle(rb.getString("LoginScreen"));
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
//        Locale.setDefault(new Locale("fr"));
//        Locale france = new Locale("fr","FR");
//
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("ResourceBundle/Nat",Locale.getDefault());
//        if (Locale.getDefault().getLanguage().equals("fr"));
//        System.out.println(resourceBundle.getString("credential"));

        launch(args);



        JDBC.closeConnection();



    }
}