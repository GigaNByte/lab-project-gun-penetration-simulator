package com.giga.controllers;

import com.giga.HibernateConnection;
import com.giga.model.Context;
import com.giga.model.Gun;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


/**
 * JavaFX Controller for GunEdit Tab
 *
 * @author GigaNByte
 * @since 1.0
 */
public class GunEditController implements Initializable {
    @FXML
    private SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
    @FXML
    private TextField gEditName;
    @FXML
    private TextField gEditAmmoName;
    @FXML
    private TextField gEditAmmoType;
    @FXML
    private Spinner<Double> gEditCalliber;
    @FXML
    private TextField gEditNation;
    @FXML
    private Spinner<Double> gEditBarrelLenght;
    @FXML
    private Text gEditErrorMessage;
    @FXML
    private Spinner<Integer> gEditMuzzleVelocity;
    @FXML
    private TableView<Gun> gPenTable;
    @FXML
    private TableColumn<Gun, Integer> gPenTable100;
    @FXML
    private TableColumn<Gun, Integer> gPenTable300;
    @FXML
    private TableColumn<Gun, Integer> gPenTable500;
    @FXML
    private TableColumn<Gun, Integer> gPenTable1000;
    @FXML
    private TableColumn<Gun, Integer> gPenTable1500;
    @FXML
    private TableColumn<Gun, Integer> gPenTable2000;
    @FXML
    private TableColumn<Gun, Integer> gPenTable2500;
    @FXML
    private TableColumn<Gun, Integer> gPenTable3000;
    @FXML
    private Integer gunIndex;
    @FXML
    private NumberAxis gEditPenChartYAxis;
    @FXML
    private NumberAxis gEditPenChartXAxis;
    @FXML
    private NumberAxis PenetrationValueAxis;
    @FXML
    private LineChart<Number, Number> gEditPenChart;



    public GunEditController(Integer gunIndex) {
        this.gunIndex = gunIndex;
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Gun editableGun = Context.getInstance().getGunTable().get(gunIndex);
        gEditName.setText(editableGun.getGunName());
        gEditAmmoType.setText(editableGun.getAmmoType());
        gEditAmmoName.setText(editableGun.getAmmoName());
        gEditNation.setText(editableGun.getNation());
        gEditName.setText(editableGun.getGunName());

        //init tableView
        gPenTable.setEditable(true);
        List<TableColumn> cells = new ArrayList<>();

        gPenTable100.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen100"));
        gPenTable300.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen300"));
        gPenTable500.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen500"));
        gPenTable1000.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen1000"));
        gPenTable1500.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen1500"));
        gPenTable2000.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen2000"));
        gPenTable2500.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen2500"));
        gPenTable3000.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen3000"));
        //add pen table row
        gPenTable.getItems().add(editableGun);

        //implements cell editing
        cells.add(gPenTable100);
        cells.add(gPenTable300);
        cells.add(gPenTable500);
        cells.add(gPenTable1000);
        cells.add(gPenTable1500);
        cells.add(gPenTable2000);
        cells.add(gPenTable2500);
        cells.add(gPenTable3000);

        //load values for spinners
        gEditMuzzleVelocity.getValueFactory().setValue(editableGun.getMuzzleVelocity());
        gEditCalliber.getValueFactory().setValue(editableGun.getCaliber());
        gEditBarrelLenght.getValueFactory().setValue(editableGun.getBarrelLenght());


        //speed up spinners
        List<Spinner> Spinners= Arrays.asList(gEditCalliber,gEditBarrelLenght,gEditMuzzleVelocity);
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


        //cell editing
        for (TableColumn cell : cells) {
            cell.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        }
        gPenTable100.setOnEditCommit(
                (TableColumn.CellEditEvent<Gun, Integer> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPen100(t.getNewValue());
                });
        gPenTable300.setOnEditCommit(
                (TableColumn.CellEditEvent<Gun, Integer> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPen300(t.getNewValue());
                });
        gPenTable500.setOnEditCommit(
                (TableColumn.CellEditEvent<Gun, Integer> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPen500(t.getNewValue());
                });
        gPenTable1000.setOnEditCommit(
                (TableColumn.CellEditEvent<Gun, Integer> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPen1000(t.getNewValue());
                });
        gPenTable1500.setOnEditCommit(
                (TableColumn.CellEditEvent<Gun, Integer> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPen1500(t.getNewValue());
                });
        gPenTable2000.setOnEditCommit(
                (TableColumn.CellEditEvent<Gun, Integer> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPen2000(t.getNewValue());
                });
        gPenTable2500.setOnEditCommit(
                (TableColumn.CellEditEvent<Gun, Integer> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPen2500(t.getNewValue());
                });
        gPenTable3000.setOnEditCommit(
                (TableColumn.CellEditEvent<Gun, Integer> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setPen3000(t.getNewValue());
                });
        handleChart();

    }


    /**
     * Handles gEditPenChart
     *
     * @author GigaNByte
     * @since 1.0
     */
    public void handleChart() {
        Gun editableGun = Context.getInstance().getGunTable().get(gunIndex);

        gEditPenChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.setName("RHA BHN 270 Normalized US NBL Criteria");
        series.getData().add(new XYChart.Data(100, editableGun.getPen100()));
        series.getData().add(new XYChart.Data(300, editableGun.getPen300()));
        series.getData().add(new XYChart.Data(500, editableGun.getPen500()));
        series.getData().add(new XYChart.Data(1000, editableGun.getPen1000()));
        series.getData().add(new XYChart.Data(1500, editableGun.getPen1500()));
        series.getData().add(new XYChart.Data(2000, editableGun.getPen2000()));
        series.getData().add(new XYChart.Data(2500, editableGun.getPen2500()));
        series.getData().add(new XYChart.Data(3000, editableGun.getPen3000()));

        gEditPenChart.getData().add(series);

    }

    /**
     * Submits edit gun form
     *
     * @author GigaNByte
     * @since 1.0
     */
    @FXML
    public void submitGunEdit() throws IOException {

        try {
            if (gEditName.getText().isEmpty() || gEditName.getText().isEmpty() || gEditAmmoName.getText().isEmpty() || gEditAmmoType.getText().isEmpty() || gEditMuzzleVelocity.getValue() <= 0D || gEditCalliber.getValue() <= 0D || gEditBarrelLenght.getValue() <= 0D) {
                gEditErrorMessage.setText("Some fields are missing");
                gEditErrorMessage.setStyle("-fx-text-inner-color: red;");
            } else {
                Gun newGun = gPenTable.getItems().get(0);
                newGun.setGunName(gEditName.getText());
                newGun.setNation(gEditNation.getText());
                newGun.setAmmoName(gEditAmmoName.getText());
                newGun.setAmmoType(gEditAmmoType.getText());
                newGun.setBarrelLenght(gEditBarrelLenght.getValue().doubleValue());
                newGun.setMuzzleVelocity(gEditMuzzleVelocity.getValue());
                newGun.setCaliber(gEditCalliber.getValue().doubleValue());
                Context.getInstance().saveOrUpdateEntity(newGun);
                Context.getInstance().refreshFireTestTable();
                Context.getInstance().refreshVehicleTable();
                handleChart();
                gEditErrorMessage.setStyle("-fx-text-inner-color: green;-fx-text-fill: green;");
                gEditErrorMessage.setText("Edited gun successfully");
                gEditErrorMessage.setText("Vehicles and FireTests updated");
            }
            gEditErrorMessage.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            gEditErrorMessage.setStyle("-fx-text-inner-color: red;-fx-text-fill: red;");
            gEditErrorMessage.setText("Error at editing gun");
            gEditErrorMessage.setVisible(true);
        }

    }
}
