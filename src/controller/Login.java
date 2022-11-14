package controller;

import DBAccess.DBCountries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.Countries;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginClick(ActionEvent actionEvent) {
    }


    /*public void showMeBtn(javafx.event.ActionEvent actionEvent) {
        ObservableList<Countries> Countries = DBCountries.getAllCountries();
        for (Countries C : Countries){
            System.out.println("Country ID: " + C.getId() + " Name: " + C.getName());
        }
    }*/

}
