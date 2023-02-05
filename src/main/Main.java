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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
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
    public static void main(String[] args) throws IOException {

        JDBC.openConnection();


        //Webinar follow along :
//        //File name and item variables
//        String filename = "login_activity.txt", item;
//        //Create Scanner Object
//        Scanner keyboard = new Scanner(System.in);
//        //Get item count
//        System.out.println("How many items do you have?");
//        int numItems = keyboard.nextInt();
//        //Clear Keyboard buffer
//        keyboard.nextLine();
//        //Create filewriter object
//        FileWriter fwriter = new FileWriter(filename, true);
//        //Create and open file
//        PrintWriter outputFile = new PrintWriter("filename");
//        //Get items and add to file
//        for (int i = 0; i < numItems; i++){
//            System.out.println("Enter item " + (i+1) + ": ");
//            item = keyboard.nextLine();
//            outputFile.println(item);
//        }
//        //Close file
//        outputFile.close();
//        System.out.println("File written");

//        ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
//        ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("America")).forEach(System.out::println);

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