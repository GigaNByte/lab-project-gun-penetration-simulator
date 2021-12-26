package com.giga.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.giga.model.Context;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class MainController implements Initializable {

    // variable name must match name of Controller witch is name of Class of Controller ...
    //https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/doc-files/introduction_to_fxml.html#nested_controllers
    //https://community.oracle.com/tech/developers/discussion/3561276/refresh-included-tab-page
    @FXML private VehicleFormController VehicleFormController ;
    @FXML private GunFormController GunFormController ;
    @FXML private FireTestFormController FireTestFormController ;
    @FXML private Tab tab;
    @FXML private final static MainController instance = new MainController();

    @FXML private TabPane MainTabPane;

    @FXML
    public void vehicleTabChanged(Event event) {
        VehicleFormController.refresh();
    }
    @FXML
    public void gunTabChanged(Event event) {
        VehicleFormController.refresh();
    }
    @FXML
    public void fireTestTabChanged(Event event) {
        FireTestFormController.refresh();
    }
    public void addTab(Tab tab){

        MainTabPane.getTabs().add(tab);
        MainTabPane.getSelectionModel().select(tab);

    }

    public static MainController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
