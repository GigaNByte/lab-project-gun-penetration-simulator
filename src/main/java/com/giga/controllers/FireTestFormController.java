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
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
/**
Javafx Controller for FireTestForm tab
 */
public class FireTestFormController implements Initializable {
    @FXML
    private SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
    //form
    @FXML
    private TextField ftFormName;
    @FXML
    private ChoiceBox ftFormVehicle;
    @FXML
    private ChoiceBox ftFormTargetVehicle;
    @FXML
    private ChoiceBox ftFormTargetVehiclePart;
    @FXML
    private Spinner ftFormShotAngle;
    @FXML
    private ChoiceBox ftFormShotDistance;
    @FXML
    private Text ftFormErrorMessage;

    //table
    @FXML
    private TableView<FireTest> ftTable;
    @FXML
    private TableColumn<FireTest, String> ftNameColumn;
    @FXML
    private TableColumn<FireTest, FireTest.TestResult> ftResultColumn;
    @FXML
    private TableColumn<FireTest, Vehicle> ftVehicleColumn;
    @FXML
    private TableColumn<FireTest, Vehicle> ftTargetVehicleColumn;
    @FXML
    private TableColumn<FireTest, FireTest.VehiclePart> ftTargetVehiclePartColumn;
    @FXML
    private TableColumn<FireTest, Integer> ftShotAngleColumn;
    @FXML
    private TableColumn<FireTest, Integer> ftShotDistanceColumn;
    @FXML
    private TableColumn ftDelete;

    private ObservableList distances = FXCollections.observableArrayList(Arrays.asList(100, 300, 500, 1000, 1500, 2000, 2500, 3000));

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO:Refactor: Refresh implementation is somehow different than in other controllers!

        ftNameColumn.setCellValueFactory(new PropertyValueFactory<FireTest, String>("testName"));
        ftVehicleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Vehicle>("vehicle"));
        ftTargetVehicleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Vehicle>("targetVehicle"));
        ftTargetVehiclePartColumn.setCellValueFactory(new PropertyValueFactory<FireTest, FireTest.VehiclePart>("targetVehiclePart"));
        ftResultColumn.setCellValueFactory(new PropertyValueFactory<FireTest, FireTest.TestResult>("result"));
        ftShotAngleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Integer>("shotAngle"));
        ftShotDistanceColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Integer>("shotDistance"));

        List<FireTest.VehiclePart> allVehicleParts = Arrays.asList(FireTest.VehiclePart.class.getEnumConstants());
        ftFormTargetVehiclePart.getItems().setAll(allVehicleParts);
        ftFormTargetVehiclePart.getSelectionModel().selectFirst();
        ftFormShotDistance.setItems(distances);
        ftFormShotDistance.getSelectionModel().selectFirst();

        ftTable.setItems(Context.getInstance().getFireTestTable());

        //handle delete button
        ftDelete.setCellValueFactory(new PropertyValueFactory<FireTest, Integer>("id"));
        Callback<TableColumn<FireTest, String>, TableCell<FireTest, Integer>> cellDeleteFactory =
                new Callback<TableColumn<FireTest, String>, TableCell<FireTest, Integer>>() {
                    @Override
                    public TableCell call(final TableColumn<FireTest, String> column) {
                        final TableCell<FireTest, Integer> cell = new TableCell<FireTest, Integer>() {
                            final Button btn = new Button(column.getText());

                            @Override
                            public void updateItem(Integer ftID, boolean empty) {
                                super.updateItem(ftID, empty);
                                if (empty) {
                                    //TODO:add some svg graphic
                                    setGraphic(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        //delete gun
                                        Context.getInstance().deleteEntityById(FireTest.class, ftID);
                                        //updates gTable
                                        ftTable.setItems(Context.getInstance().getFireTestTable());
                                    });
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                        return cell;
                    }
                };
        ftDelete.setCellFactory(cellDeleteFactory);


        ftFormTargetVehicle.setItems(Context.getInstance().getVehicleTable());
        ftFormTargetVehicle.getSelectionModel().selectFirst();
        ftFormVehicle.setItems(Context.getInstance().getVehicleTable());
        ftFormVehicle.getSelectionModel().selectFirst();
    }


    @FXML
    public void submitFireTest() {
        //validate
        if ((((Vehicle) ftFormVehicle.getValue()).getVehicleName().isEmpty()) ||
                (((Vehicle) ftFormTargetVehicle.getValue()).getVehicleName().isEmpty()) ||
                ((ftFormTargetVehiclePart.getValue()) == null) ||
                (Integer) ftFormShotAngle.getValue() < -180 || (Integer) ftFormShotAngle.getValue() > 180 ||
                (Integer) ftFormShotDistance.getValue() <= 0
        ) {
            ftFormErrorMessage.setText("Some fields are missing");
            ftFormErrorMessage.setStyle("-fx-text-inner-color: red;");
            ftFormErrorMessage.setVisible(true);
        } else {
            try {
                //TODO: create Firetest Creator/pseudo constructor and replace code below
                FireTest newFireTest = new FireTest();

                newFireTest.setTestName(ftFormName.getText());
                newFireTest.setShotAngle((Integer) ftFormShotAngle.getValue());
                newFireTest.setShotDistance((Integer) ftFormShotDistance.getValue());
                newFireTest.setVehicle((Vehicle) ftFormVehicle.getValue());
                newFireTest.setTargetVehicle((Vehicle) ftFormTargetVehicle.getValue());
                newFireTest.setTargetVehiclePart((FireTest.VehiclePart) ftFormTargetVehiclePart.getValue());
                newFireTest.calculateResult();
                Context.getInstance().saveOrUpdateEntity(newFireTest);

                ftTable.setItems(Context.getInstance().getFireTestTable());

                ftFormErrorMessage.setStyle("-fx-text-inner-color: green;-fx-text-fill: green;");
                ftFormErrorMessage.setText("Added fire test successfully");
                ftFormErrorMessage.setVisible(true);


            } catch (Exception e) {
                System.out.println(e);
                ftFormErrorMessage.setStyle("-fx-text-inner-color: red;-fx-text-fill: red;");
                ftFormErrorMessage.setText("Error at adding fire test");
                ftFormErrorMessage.setVisible(true);
                e.printStackTrace();
            }
        }
    }





}
