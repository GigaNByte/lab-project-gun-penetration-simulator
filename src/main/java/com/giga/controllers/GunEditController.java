package com.giga.controllers;

import com.giga.HibernateConnection;
import com.giga.model.Context;
import com.giga.model.Gun;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


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
    private TextField gEditCalliber;
    @FXML
    private TextField gEditNation;
    @FXML
    private TextField gEditBarrelLenght;
    @FXML
    private Text gEditErrorMessage;
    @FXML
    private TextField gEditMuzzleVelocity;
    @FXML
    private TableView<Gun> gPenTable;
    @FXML
    private TableColumn gPenTable100;
    @FXML
    private TableColumn gPenTable300;
    @FXML
    private TableColumn gPenTable500;
    @FXML
    private TableColumn gPenTable1000;
    @FXML
    private TableColumn gPenTable1500;
    @FXML
    private TableColumn gPenTable2000;
    @FXML
    private TableColumn gPenTable3000;
    @FXML
    private Integer gunIndex;

    public GunEditController(Integer gunIndex) {
        this.gunIndex = gunIndex;
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gEditName
        gPenTable100.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen100"));
        gPenTable300.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen300"));
        gPenTable500.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen500"));
        gPenTable1000.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen1000"));
        gPenTable1500.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen1500"));
        gPenTable2000.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen2000"));
        gPenTable3000.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("pen3000"));
        gPenTable.getItems().add(Context.getInstance().getGunTable().get(gunIndex));
        gPenTable.setEditable(true);

        //retrieves gun dropdown list
    }

    public void refresh(URL location, ResourceBundle resources) {
        initialize(null, null);
    }

    @FXML
    public void submitGunEdit() throws IOException {

    }
}
