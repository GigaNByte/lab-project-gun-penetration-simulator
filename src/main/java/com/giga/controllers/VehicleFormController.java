package com.giga.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.giga.HibernateConnection;
import com.giga.model.Context;
import com.giga.model.Gun;
import com.giga.model.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class VehicleFormController implements Initializable {
    //form
    @FXML private SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
    @FXML private TextField vFormName;
    @FXML private TextField vFormNation;
    @FXML private Spinner vFormFrontArmorThick ;
    @FXML private Spinner vFormSideArmorThick;
    @FXML private Spinner vFormFrontArmorAngle;
    @FXML private Spinner vFormSideArmorAngle;
    @FXML private ChoiceBox vFormGun;
    @FXML private Text vFormErrorMessage;
    //table
    @FXML private TableView<Vehicle> vTable ;
    @FXML private TableColumn<Vehicle,String> vNameColumn;
    @FXML private TableColumn<Vehicle,Gun> vGunColumn;
    @FXML private TableColumn<Vehicle,String> vNationColumn;
    @FXML private TableColumn<Vehicle,Integer> vFrontThickColumn;
    @FXML private TableColumn<Vehicle,Integer> vFrontArmorAngleColumn;
    @FXML private TableColumn<Vehicle,Integer> vSideThickColumn;
    @FXML private TableColumn<Vehicle,Integer> vSideArmorAngleColumn;



    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vNameColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleName"));
        vNationColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("nation"));
        vGunColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Gun>("gun"));
        vFrontThickColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("frontArmorThickness"));
        vSideThickColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("sideArmorThickness"));
        vFrontArmorAngleColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("frontArmorAngle"));
        vSideArmorAngleColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("sideArmorAngle"));
        vTable.setItems(Context.getInstance().getVehicleTable());

        //retrieves gun dropdown list
        vFormGun.setItems(Context.getInstance().getGunTable());
        vFormGun.getSelectionModel().selectFirst();

        refresh();
    }

    @FXML
    public void refresh() {


    }
    @FXML
    public void submitVehicleForm() {


        if (vFormName.getText().isEmpty() || vFormNation.getText().isEmpty() || (Integer)vFormFrontArmorThick.getValue() < 0
            || (Integer)vFormSideArmorThick.getValue() < 0 || (Integer)vFormFrontArmorAngle.getValue() < 0
                || (Integer)vFormSideArmorAngle.getValue() < 0 ){
            vFormErrorMessage.setText("Some fields are missing");
            vFormErrorMessage.setStyle("-fx-text-inner-color: red;");
            vFormErrorMessage.setVisible(true);
        } else {
            // code below should belong to DAO class
            Vehicle newVehicle = new Vehicle();
            //? Should i just cast it straight away ? is it type safe? Same for Integer/Double?
            newVehicle.setGun((Gun) vFormGun.getValue());
            newVehicle.setNation(vFormNation.getText());
            newVehicle.setVehicleName(vFormName.getText());
            newVehicle.setFrontArmorThickness((Integer)vFormFrontArmorThick.getValue());
            newVehicle.setSideArmorThickness((Integer)vFormSideArmorThick.getValue());
            newVehicle.setFrontArmorAngle((Integer)vFormFrontArmorAngle.getValue());
            newVehicle.setSideArmorAngle((Integer)vFormSideArmorAngle.getValue());

            try {
                //TODO: replace code below as  addVehicle method in Context
                Context.getInstance().saveOrUpdateEntity(newVehicle);
                vTable.setItems(Context.getInstance().getVehicleTable());
                //TODO:Color does not work
                vFormErrorMessage.setStyle("-fx-text-inner-color: green;-fx-text-fill: green;");
                vFormErrorMessage.setText("Added gun successfully");
                vFormErrorMessage.setVisible(true);
                
            }catch (Exception e){
                vFormErrorMessage.setStyle("-fx-text-inner-color: red;-fx-text-fill: red;");
                vFormErrorMessage.setText("Error at adding vehicle");
                vFormErrorMessage.setVisible(true);
            }
        }

    }


}
