
package com.giga.model;

import com.giga.HibernateConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
//TODO: Make this Singleton Thread Safe

/**
 * Singleton class that handles queries and stores state shared across all fxml tab controllers in app
 *
 * @author GigaNByte
 * @since 1.0
 */
public class Context {
    private SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
    @FXML
    private ObservableList<FireTest> fireTestTable = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Vehicle> vehicleTable = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Gun> gunTable = FXCollections.observableArrayList();


    /**
     * @return FireTests Entities from Database
     */
    public ObservableList<FireTest> getFireTestTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<FireTest> allFireTests = session.createQuery("FROM FireTest ").list();
        session.getTransaction().commit();
        session.close();
        fireTestTable.setAll(allFireTests);
        return fireTestTable;
    }

    /**
     * Adds or Updates entity in the database
     *
     * @param entityObject entity Object
     */
    //TODO:Provide Strict Object Typing
    public void saveOrUpdateEntity(Object entityObject) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(entityObject);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * @return Vehicles Entities from Database
     */
    public ObservableList<Vehicle> getVehicleTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Vehicle> allVehicles = session.createQuery("FROM Vehicle").list();
        session.getTransaction().commit();
        session.close();
        vehicleTable.setAll(allVehicles);
        return vehicleTable;
    }

    /**
     * @return Guns Entities from Database
     */
    public ObservableList<Gun> getGunTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Gun> allGuns = session.createQuery("FROM Gun ").list();
        session.getTransaction().commit();
        session.close();
        gunTable.setAll(allGuns);
        return gunTable;
    }

    /**
     * @return Gun
     */
    public ObservableList<Gun> getGun() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Gun> allGuns = session.createQuery("FROM Gun ").list();
        session.getTransaction().commit();
        session.close();
        gunTable.setAll(allGuns);
        return gunTable;
    }

    /**
     * @param entityClass class object of entity to be deleted
     * @param id          id of entity
     * @param <T>         class name of entity to be deleted
     */
    public <T> void deleteEntityById(Class<T> entityClass, Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        T entity = session.find(entityClass, id);
        session.remove(entity);
        session.getTransaction().commit();
        session.close();
    }

    private final static Context instance = new Context();

    /**
     * @return singleton instance of Context object
     */
    public static Context getInstance() {
        return instance;
    }


}