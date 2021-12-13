package com.giga.controllers;

import java.io.IOException;

import com.giga.App;
import com.giga.HibernateConnection;
import com.giga.model.Gun;
import com.giga.model.Vehicle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class GunFormController {
    @FXML private TextField gFormName;
    @FXML private TextField gFormAmmoName;
    @FXML private TextField gFormAmmoType;
    @FXML private TextField gFormCalliber;
    @FXML private TextField gFormNation;
    @FXML private TextField gFormBarrelLenght;
    @FXML private Text gFormErrorMessage;
    @FXML private TextField gFormMuzzleVelocity;
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

            SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(newGun);
            session.getTransaction().commit();
            session.close();
            gFormErrorMessage.setStyle("-fx-text-inner-color: green;-fx-text-fill: green;");
            gFormErrorMessage.setText("Added gun successfully");
            gFormErrorMessage.setVisible(true);

        }
    }
}
