package com.giga.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.giga.HibernateConnection;
import com.giga.model.Gun;
import com.giga.model.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class VehicleFormController implements Initializable {
    @FXML
    private TextField vFormName;
    @FXML
    private TextField vFormNation;
    @FXML
    private Spinner vFormFrontArmorThick;
    @FXML
    private Spinner vFormSideArmorThick;
    @FXML
    private Spinner vFormFrontArmorAngle;
    @FXML
    private Spinner vFormSideArmorAngle;
    @FXML
    private ChoiceBox vFormGun;
    @FXML
    private Text vFormErrorMessage;
    @FXML
    private SessionFactory sessionFactory = HibernateConnection.getSessionFactory();

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //test
        Gun testGun2 = new Gun();
        testGun2.setGunName("85mm/L52 (D-5T)");
        testGun2.setAmmoName("BR-365K");
        testGun2.setAmmoType("AP");
        testGun2.setBarrelLenght(4420.);
        testGun2.setCaliber(85.);
        testGun2.setMuzzleVelocity(792);
        testGun2.setNation("USSR");
        testGun2.setPen100(127);
        SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(testGun2);
        session.getTransaction().commit();
        session.close();

        ObservableList<Gun> guns = FXCollections.observableArrayList();
        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Gun> all_guns = session.createQuery("FROM Gun").list();
        session.getTransaction().commit();
        session.close();
        guns.addAll(all_guns);
        vFormGun.getItems().addAll(guns);
        vFormGun.getSelectionModel().selectFirst();
    }

    @FXML
    public void refresh() {
        this.initialize(null,null);
    }
    @FXML
    public void submitVehicleForm() {


        if (vFormName.getText().isEmpty() || vFormNation.getText().isEmpty() || (Integer)vFormFrontArmorThick.getValue() <= 0
            || (Integer)vFormSideArmorThick.getValue() <= 0 || (Integer)vFormFrontArmorAngle.getValue() <= 0
                || (Integer)vFormSideArmorAngle.getValue() <= 0 ){
            vFormErrorMessage.setText("Some fields are missing");
            vFormErrorMessage.setStyle("-fx-text-inner-color: red;");
            vFormErrorMessage.setVisible(true);
        } else {
            // code below should belong to DAO class
            Vehicle newVehicle= new Vehicle();
            //? Should i just cast it straight away ? is it type safe? Same for Integer/Double?
            newVehicle.setGun((Gun) vFormGun.getValue());
            newVehicle.setNation(vFormNation.getText());
            newVehicle.setFrontArmorThickness((Integer)vFormFrontArmorThick.getValue());
            newVehicle.setSideArmorThickness((Integer)vFormSideArmorThick.getValue());
            newVehicle.setFrontArmorAngle((Integer)vFormFrontArmorAngle.getValue());
            newVehicle.setSideArmorAngle((Integer)vFormSideArmorAngle.getValue());

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(newVehicle);
            session.getTransaction().commit();
            session.close();
            //Color does not work
            vFormErrorMessage.setStyle("-fx-text-inner-color: green;-fx-text-fill: green;");
            vFormErrorMessage.setText("Added gun successfully");
            vFormErrorMessage.setVisible(true);
        }

    }


}
