package com.giga.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.giga.App;
import com.giga.HibernateConnection;
import com.giga.model.Context;
import com.giga.model.Gun;
import com.giga.model.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class GunFormController implements Initializable {
    @FXML private SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
    @FXML private TextField gFormName;
    @FXML private TextField gFormAmmoName;
    @FXML private TextField gFormAmmoType;
    @FXML private TextField gFormCalliber;
    @FXML private TextField gFormNation;
    @FXML private TextField gFormBarrelLenght;
    @FXML private Text gFormErrorMessage;
    @FXML private TextField gFormMuzzleVelocity;
    @FXML private TableView<Gun> gTable ;
    @FXML private TableColumn gNameColumn;
    @FXML private TableColumn gAmmoNameColumn;
    @FXML private TableColumn gNationColumn;
    @FXML private TableColumn gAmmoTypeColumn;
    @FXML private TableColumn gCaliberColumn;
    @FXML private TableColumn gBarrelLenghtColumn;
    @FXML private TableColumn gMuzzleVelocityColumn;




    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gNameColumn.setCellValueFactory(new PropertyValueFactory<Gun, String>("gunName"));
        gAmmoNameColumn.setCellValueFactory(new PropertyValueFactory<Gun, String>("ammoName"));
        gNationColumn.setCellValueFactory(new PropertyValueFactory<Gun, String>("nation"));
        gAmmoTypeColumn.setCellValueFactory(new PropertyValueFactory<Gun, String>("ammoType"));
        gCaliberColumn.setCellValueFactory(new PropertyValueFactory<Gun, Double>("caliber"));
        gBarrelLenghtColumn.setCellValueFactory(new PropertyValueFactory<Gun, Double>("barrelLenght"));
        gMuzzleVelocityColumn.setCellValueFactory(new PropertyValueFactory<Gun, Integer>("muzzleVelocity"));
        gTable.setItems(Context.getInstance().getGunTable());
        //retrieves gun dropdown list
    }

    public void refresh(URL location, ResourceBundle resources) {
        initialize(null,null);
    }
    @FXML
    public void submitGunForm() throws IOException {
        if (gFormName.getText().isEmpty() || gFormName.getText().isEmpty() || gFormAmmoName.getText().isEmpty() || gFormAmmoType.getText().isEmpty()|| Double.parseDouble(gFormMuzzleVelocity.getText()) <= 0. ||  Double.parseDouble(gFormCalliber.getText()) <= 0. || Integer.parseInt(gFormBarrelLenght.getText()) <= 0 ){
            gFormErrorMessage.setText("Some fields are missing");
            gFormErrorMessage.setStyle("-fx-text-inner-color: red;");
            gFormErrorMessage.setVisible(true);
        }else{
            // code below should belong to DAO class
            Gun newGun = new Gun();
            newGun.setGunName(gFormName.getText());
            newGun.setNation(gFormNation.getText());
            newGun.setAmmoName(gFormAmmoName.getText());
            newGun.setAmmoType(gFormAmmoType.getText());
            newGun.setBarrelLenght(Double.parseDouble(gFormBarrelLenght.getText()));
            newGun.setMuzzleVelocity(Integer.parseInt(gFormMuzzleVelocity.getText()));
            newGun.setCaliber(Double.parseDouble(gFormCalliber.getText()));

            try{
                //TODO: replace code below as  addVehicle method in Context
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.persist(newGun);
                session.getTransaction().commit();
                session.close();
                gFormErrorMessage.setStyle("-fx-text-inner-color: green;-fx-text-fill: green;");
                gFormErrorMessage.setText("Added gun successfully");
                gFormErrorMessage.setVisible(true);
                gTable.setItems(Context.getInstance().getGunTable());
            }catch (Exception e){
                gFormErrorMessage.setStyle("-fx-text-inner-color: red;-fx-text-fill: red;");
                gFormErrorMessage.setText("Error at adding gun");
                gFormErrorMessage.setVisible(true);
            }

        }

    }
}
