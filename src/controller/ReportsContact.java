package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsContact {

    Parent scene;
    Stage stage;

    @FXML
    private TableColumn<?, ?> appointmentId_column;

    @FXML
    private TableColumn<?, ?> customerId_column;

    @FXML
    private RadioButton customerRadioBtn;

    @FXML
    private TableColumn<?, ?> dateFinished_column;

    @FXML
    private TableColumn<?, ?> description_column;

    @FXML
    private Button exitBtn;

    @FXML
    private ToggleGroup filter;

    @FXML
    private ComboBox<?> filterCombo;

    @FXML
    private RadioButton monthRadioBtn;

    @FXML
    private TableColumn<?, ?> startDate_column;

    @FXML
    private TableColumn<?, ?> timeFinished_column;

    @FXML
    private TableColumn<?, ?> timeStart_column;

    @FXML
    private TableColumn<?, ?> title_column;

    @FXML
    private RadioButton typeRadioBtn;

    @FXML
    private TableColumn<?, ?> type_column;

    @FXML
    void customerRadio_action(ActionEvent event) {

    }

    @FXML
    void exitBtn_action(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(getClass().getResource("/view/appointmentScreen.fxml"));

        stage.setScene(new Scene(scene));

        stage.show();
    }

    @FXML
    void filterCombo_action(ActionEvent event) {

    }

    @FXML
    void monthRadio_action(ActionEvent event) {

    }

    @FXML
    void typeRadio_action(ActionEvent event) {

    }

}
