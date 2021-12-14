package com.giga.controllers;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainController {

    // variable name must match name of Controller witch is name of Class of Controller ...
    //https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/doc-files/introduction_to_fxml.html#nested_controllers
    //https://community.oracle.com/tech/developers/discussion/3561276/refresh-included-tab-page
    @FXML private VehicleFormController VehicleFormController ;
    @FXML private GunFormController GunFormController ;
    @FXML private FireTestFormController FireTestFormController ;

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
}
