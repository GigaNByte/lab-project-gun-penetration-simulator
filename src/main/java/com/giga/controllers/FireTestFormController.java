package com.giga.controllers;


import com.giga.HibernateConnection;
import com.giga.model.Context;
import com.giga.model.FireTest;
import com.giga.model.Gun;
import com.giga.model.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.*;

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
    private Spinner ftFormShotVerticalAngle;
    @FXML
    private Spinner ftFormShotHorizontalAngle;
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
    private TableColumn<FireTest, Integer> ftShotVerticalAngleColumn;
    @FXML
    private TableColumn<FireTest, Integer> ftShotHorizontalAngleColumn;
    @FXML
    private TableColumn<FireTest, Integer> ftShotCompoundAngleColumn;
    @FXML
    private TableColumn<FireTest, Double> ftRelativeArmorThicknessColumn;
    @FXML
    private TableColumn<FireTest, Integer> ftShotDistanceColumn;
    @FXML
    private TableColumn ftDelete;

    private ObservableList distances = FXCollections.observableArrayList(Arrays.asList(100, 300, 500, 1000, 1500, 2000, 2500, 3000));

    @FXML
    private TextField ftFilterField;
    private FilteredList<FireTest> filteredData = new FilteredList<FireTest>(Context.getInstance().getFireTestTable(), p -> true);



    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO:Refactor: Refresh implementation is somehow different than in other controllers!

        ftNameColumn.setCellValueFactory(new PropertyValueFactory<FireTest, String>("testName"));
        ftVehicleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Vehicle>("vehicle"));
        ftTargetVehicleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Vehicle>("targetVehicle"));
        ftTargetVehiclePartColumn.setCellValueFactory(new PropertyValueFactory<FireTest, FireTest.VehiclePart>("targetVehiclePart"));
        ftResultColumn.setCellValueFactory(new PropertyValueFactory<FireTest, FireTest.TestResult>("result"));
        ftShotVerticalAngleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Integer>("shotVerticalAngle"));
        ftShotHorizontalAngleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Integer>("shotHorizontalAngle"));
        ftShotCompoundAngleColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Integer>("shotCompoundAngle"));
        ftRelativeArmorThicknessColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Double>("relativeArmorThickness"));
        ftShotDistanceColumn.setCellValueFactory(new PropertyValueFactory<FireTest, Integer>("shotDistance"));

        List<FireTest.VehiclePart> allVehicleParts = Arrays.asList(FireTest.VehiclePart.class.getEnumConstants());
        ftFormTargetVehiclePart.getItems().setAll(allVehicleParts);
        ftFormTargetVehiclePart.getSelectionModel().selectFirst();
        ftFormShotDistance.setItems(distances);
        ftFormShotDistance.getSelectionModel().selectFirst();


        //speed up spinners
        List<Spinner> Spinners= Arrays.asList(ftFormShotVerticalAngle);
        IncrementHandler handler = new IncrementHandler();
        for (Spinner spinner:Spinners) {
            spinner.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
            spinner.addEventFilter(MouseEvent.MOUSE_RELEASED, evt -> {
                Node node = evt.getPickResult().getIntersectedNode();
                if (node.getStyleClass().contains("increment-arrow-button") ||
                        node.getStyleClass().contains("decrement-arrow-button")) {
                    if (evt.getButton() == MouseButton.PRIMARY) {
                        handler.stop();
                    }
                }
            });
        }

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

        //bind sorted table to tableview
        Context.getInstance().getSortedFireTestTable().comparatorProperty().bind(ftTable.comparatorProperty());
        ftTable.setItems(Context.getInstance().getSortedFireTestTable());

        //adds comparator to sort by column
        //https://stackoverflow.com/questions/17958337/javafx-tableview-with-filteredlist-jdk-8-does-not-sort-by-column
        //https://stackoverflow.com/questions/50109815/javafx-tableview-sort-by-custom-rule-then-by-column-selection
        ftTable.sortPolicyProperty().set(new Callback<TableView<FireTest>, Boolean>() {
            @Override
            public Boolean call(TableView<FireTest> param) {
                final Comparator<FireTest> tableComparator = ftTable.getComparator();
                // if the column is set to unsorted, tableComparator can be null
                Comparator<FireTest> comparator = tableComparator == null ? null : new Comparator<FireTest>() {
                    @Override
                    public int compare(FireTest o1, FireTest o2) {
                            // secondly sort by the comparator that was set for the table
                            return tableComparator.compare(o1, o2);
                    }
                };
                ftTable.setItems(Context.getInstance().getFilteredFireTestTable().sorted(comparator));
                return true;
            }
        });

        ftFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            Context.getInstance().getSortedFireTestTable().comparatorProperty().bind(ftTable.comparatorProperty());

            Context.getInstance().getFilteredFireTestTable().setPredicate(fireTest -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (fireTest.getTestName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (fireTest.getVehicle().getVehicleName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (fireTest.getTargetVehicle().getVehicleName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
            ftTable.setItems(Context.getInstance().getSortedFireTestTable());
        });


    }


    @FXML
    public void submitFireTest() {
        //validate
        if ((((Vehicle) ftFormVehicle.getValue()).getVehicleName().isEmpty()) ||
                (((Vehicle) ftFormTargetVehicle.getValue()).getVehicleName().isEmpty()) ||
                ((ftFormTargetVehiclePart.getValue()) == null) ||
                (Integer) ftFormShotVerticalAngle.getValue() < -90 || (Integer) ftFormShotVerticalAngle.getValue() > 90 ||
                (Integer) ftFormShotDistance.getValue() < 0 || (Integer) ftFormShotHorizontalAngle.getValue() < 0 || (Integer) ftFormShotHorizontalAngle.getValue() > 90
        ) {
            ftFormErrorMessage.setText("Some fields are missing");
            ftFormErrorMessage.setStyle("-fx-text-inner-color: red;");
            ftFormErrorMessage.setVisible(true);
        } else {
            try {
                //TODO: create Firetest Creator/pseudo constructor and replace code below
                FireTest newFireTest = new FireTest();

                newFireTest.setTestName(ftFormName.getText());
                newFireTest.setShotVerticalAngle((Integer) ftFormShotVerticalAngle.getValue());
                newFireTest.setShotHorizontalAngle((Integer) ftFormShotHorizontalAngle.getValue());
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
