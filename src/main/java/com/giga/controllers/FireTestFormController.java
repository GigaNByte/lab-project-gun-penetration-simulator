package com.giga.controllers;


import com.giga.HibernateConnection;
import com.giga.model.Context;
import com.giga.model.FireTest;
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

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FireTestFormController implements Initializable {
    @FXML private SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
    //form
    @FXML private TextField ftFormName;
    @FXML private ChoiceBox ftFormVehicle;
    @FXML private ChoiceBox ftFormTargetVehicle ;
    @FXML private ChoiceBox ftFormTargetVehiclePart;
    @FXML private Spinner ftFormShotAngle;
    @FXML private Spinner ftFormShotDistance;
    @FXML private Text ftFormErrorMessage;
    //table

    @FXML private TableView<FireTest> ftTable ;
    @FXML private TableColumn<FireTest,String> ftNameColumn;
    @FXML private TableColumn<FireTest,Vehicle> ftVehicleColumn;
    @FXML private TableColumn<FireTest,Vehicle> ftTargetVehicleColumn;
    @FXML private TableColumn<FireTest, FireTest.VehiclePart> ftTargetVehiclePartColumn;
    @FXML private TableColumn<FireTest,Integer> ftShotAngleColumn;
    @FXML private TableColumn<FireTest,Integer> ftShotDistanceColumn;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ftNameColumn.setCellValueFactory(new PropertyValueFactory<FireTest, String>("testName"));
        ftVehicleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Vehicle>("vehicle"));
        ftTargetVehicleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Vehicle>("targetVehicle"));
        ftTargetVehiclePartColumn.setCellValueFactory(new PropertyValueFactory<FireTest, FireTest.VehiclePart>("targetVehiclePart"));
        ftShotAngleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Integer>("shotAngle"));
        ftShotDistanceColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Integer>("shotDistance"));

        List<FireTest.VehiclePart> allVehicleParts = Arrays.asList(FireTest.VehiclePart.class.getEnumConstants());
        ftFormTargetVehiclePart.getItems().setAll(allVehicleParts);
        ftFormTargetVehiclePart.getSelectionModel().selectFirst();

        ftTable.setItems(Context.getInstance().getFireTestTable());

        refresh();
    }
    @FXML
    public void refresh() {
        //retrieves gun dropdown list
        ObservableList<Vehicle> vehicles = Context.getInstance().getVehicleTable();
        ftFormTargetVehicle.getItems().setAll(vehicles);
        ftFormTargetVehicle.getSelectionModel().selectFirst();
        ftFormVehicle.getItems().setAll(vehicles);
        ftFormVehicle.getSelectionModel().selectFirst();
    }

    @FXML
    public void submitFireTest() {
        //validate
        if ((((Vehicle)ftFormVehicle.getValue()).getVehicleName().isEmpty()) ||
                (((Vehicle)ftFormTargetVehicle.getValue()).getVehicleName().isEmpty()) ||
                ((ftFormTargetVehiclePart.getValue()) == null)||
                (Integer)ftFormShotAngle.getValue() <= 0 || (Integer)ftFormShotAngle.getValue() > 180 ||
                (Integer)ftFormShotDistance.getValue() <= 0
        ){
            ftFormErrorMessage.setText("Some fields are missing");
            ftFormErrorMessage.setStyle("-fx-text-inner-color: red;");
            ftFormErrorMessage.setVisible(true);
        }else{
            try{
                //TODO: create Firetest Creator/pseudo constructor and replace code below
                FireTest newFireTest = new FireTest();
                newFireTest.setResult(FireTest.TestResult.PENETRATION);
                newFireTest.setTestName(ftFormName.getText());
                newFireTest.setShotAngle((Integer)ftFormShotAngle.getValue());
                newFireTest.setShotDistance((Integer)ftFormShotDistance.getValue());
                newFireTest.setVehicle((Vehicle) ftFormVehicle.getValue());
                newFireTest.setTargetVehicle((Vehicle) ftFormTargetVehicle.getValue());
                newFireTest.setTargetVehiclePart((FireTest.VehiclePart) ftFormTargetVehiclePart.getValue());

                //TODO: replace code below as  addVehicle method in Context
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.persist(newFireTest);
                session.getTransaction().commit();
                session.close();
                ftTable.setItems(Context.getInstance().getFireTestTable());

                ftFormErrorMessage.setStyle("-fx-text-inner-color: green;-fx-text-fill: green;");
                ftFormErrorMessage.setText("Added fire test successfully");
                ftFormErrorMessage.setVisible(true);


            }catch (Exception e){
                System.out.println(e);
                ftFormErrorMessage.setStyle("-fx-text-inner-color: red;-fx-text-fill: red;");
                ftFormErrorMessage.setText("Error at adding fire test");
                ftFormErrorMessage.setVisible(true);
            }
        }
    }


}
