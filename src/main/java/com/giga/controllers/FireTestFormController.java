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

        ftFormTargetVehicle.getItems().setAll(Context.getInstance().getVehicleTable());
        ftFormTargetVehicle.getSelectionModel().selectFirst();
        ftFormVehicle.getItems().setAll(Context.getInstance().getVehicleTable());
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
                newFireTest.setResult(calculatePenetration());
                newFireTest.setTestName(ftFormName.getText());
                newFireTest.setShotAngle((Integer) ftFormShotAngle.getValue());
                newFireTest.setShotDistance((Integer) ftFormShotDistance.getValue());
                newFireTest.setVehicle((Vehicle) ftFormVehicle.getValue());
                newFireTest.setTargetVehicle((Vehicle) ftFormTargetVehicle.getValue());
                newFireTest.setTargetVehiclePart((FireTest.VehiclePart) ftFormTargetVehiclePart.getValue());

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
            }
        }
    }

    //Calculates result of fire test
    private FireTest.TestResult calculatePenetration() {
        Vehicle testVehicle = (Vehicle) ftFormVehicle.getValue();
        Vehicle targetVehicle = (Vehicle) ftFormTargetVehicle.getValue();
        Double armorThickness = 0d;
        Double effectiveArmorThickness = 0d;
        Double absoluteShotAngle = 0d;
        Integer rhaGunPenetration = 0;

        //TODO:Implement Armor part Class or extend enum to contain thickness value
        //TODO:Implement second Shot angle variable (vertical angle)
        //calculates effective armor thickness
        if (ftFormTargetVehiclePart.getValue() == FireTest.VehiclePart.FRONT_ARMOR) {
            armorThickness = Double.valueOf(targetVehicle.getFrontArmorThickness());
            absoluteShotAngle = (double) (targetVehicle.getFrontArmorAngle() - ((Integer) ftFormShotAngle.getValue()));

        } else if (ftFormTargetVehiclePart.getValue() == FireTest.VehiclePart.SIDE_ARMOR) {
            armorThickness = Double.valueOf(targetVehicle.getSideArmorThickness());
            absoluteShotAngle = (double) (targetVehicle.getSideArmorAngle() - ((Integer) ftFormShotAngle.getValue()));
        }

        if (absoluteShotAngle > 0) {
            effectiveArmorThickness = armorThickness / Math.sin(Math.toRadians(Math.abs(absoluteShotAngle)));
        } else if ((absoluteShotAngle < 0)) {
            effectiveArmorThickness = armorThickness / Math.cos(Math.toRadians(Math.abs(absoluteShotAngle)));
        }

        //TODO:Implement "Shell Critical Bounce Angle" variable dependent from ammo type
        //TODO: Penetration values could be stored here as Map

        switch ((Integer) ftFormShotDistance.getValue()) {
            case 100:
                rhaGunPenetration = testVehicle.getGun().getPen100();
                break;
            case 300:
                rhaGunPenetration = testVehicle.getGun().getPen300();
                break;
            case 500:
                rhaGunPenetration = testVehicle.getGun().getPen500();
            case 1000:
                rhaGunPenetration = testVehicle.getGun().getPen1000();
                break;
            case 1500:
                rhaGunPenetration = testVehicle.getGun().getPen1500();
                break;
            case 2000:
                rhaGunPenetration = testVehicle.getGun().getPen2000();
                break;
            case 2500:
                rhaGunPenetration = testVehicle.getGun().getPen2500();
                break;
            case 3000:
                rhaGunPenetration = testVehicle.getGun().getPen3000();
                break;
            default:
                // code block
        }
        System.out.println("=========Firetest========");
        System.out.println("actual: " + armorThickness);
        System.out.printf("efective: %f\n", effectiveArmorThickness);
        System.out.println("abs angle: " + absoluteShotAngle);
        System.out.println("shot angle" + (Integer) ftFormShotAngle.getValue());
        System.out.println("armor angle" + targetVehicle.getFrontArmorAngle());
        System.out.println("gun penetration: " + rhaGunPenetration);
        System.out.println("=========================");
        if (rhaGunPenetration >= effectiveArmorThickness) {
            return FireTest.TestResult.PENETRATION;
        } else {
            return FireTest.TestResult.NO_PENETRATION;
        }


    }


}
