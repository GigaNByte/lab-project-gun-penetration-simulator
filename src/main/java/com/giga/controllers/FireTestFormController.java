package com.giga.controllers;


import com.giga.HibernateConnection;
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
    @FXML private ObservableList<FireTest> ftTableItems = FXCollections.observableArrayList();
    @FXML private TableView<FireTest> ftTable ;
    @FXML private TableColumn<FireTest,String> ftNameColumn;
    @FXML private TableColumn<FireTest,Vehicle> ftVehicleColumn;
    @FXML private TableColumn<FireTest,Vehicle> ftTargetVehicleColumn;
    @FXML private TableColumn<FireTest, FireTest.VehiclePart> ftTargetVehiclePartColumn;
    @FXML private TableColumn<FireTest,Integer> ftShotAngleColumn;
    @FXML private TableColumn<FireTest,Integer> ftShotDistanceColumn;
    ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();

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

        ftFormTargetVehiclePart.getItems().addAll(allVehicleParts);
        ftFormTargetVehiclePart.getSelectionModel().selectFirst();

        ftTable.setItems(ftTableItems);
        refresh();
    }
    @FXML
    public void refresh() {
        //retrieves gun dropdown list

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Vehicle> allVehicles = session.createQuery("FROM Vehicle").list();
        session.getTransaction().commit();
        session.close();
        vehicles.addAll(allVehicles);
        ftFormTargetVehicle.getItems().addAll(vehicles);
        ftFormTargetVehicle.getSelectionModel().selectFirst();
        ftFormVehicle.getItems().addAll(vehicles);
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
            FireTest newFireTest = new FireTest();
            newFireTest.setResult(FireTest.TestResult.PENETRATION);
            newFireTest.setTestName(ftFormName.getText());
            newFireTest.setShotAngle((Integer)ftFormShotAngle.getValue());
            newFireTest.setShotDistance((Integer)ftFormShotDistance.getValue());
            newFireTest.setVehicle((Vehicle) ftFormVehicle.getValue());
            newFireTest.setTargetVehicle((Vehicle) ftFormTargetVehicle.getValue());
            newFireTest.setTargetVehiclePart((FireTest.VehiclePart) ftFormTargetVehiclePart.getValue());

            ftFormErrorMessage.setStyle("-fx-text-inner-color: green;-fx-text-fill: green;");
            ftFormErrorMessage.setText("Added gun successfully");
            ftFormErrorMessage.setVisible(true);
            ftTableItems.add(newFireTest);
        }
    }


}
