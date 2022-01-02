
package com.giga.model;

import com.giga.HibernateConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import javax.persistence.Query;
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
    Refreshes FireTest Entities from Database
 */
    public void refreshFireTestTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<FireTest> allFireTests = session.createQuery("FROM FireTest ").list();
        session.getTransaction().commit();
        session.close();
        fireTestTable.setAll(allFireTests);
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
     Refreshes VehicleTable Entities from Database
     */
    public void refreshVehicleTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Vehicle> allVehicles = session.createQuery("FROM Vehicle").list();
        session.getTransaction().commit();
        session.close();
        vehicleTable.setAll(allVehicles);
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
     deletes Vehicle by id (propagates deletion of linked entities: FireTest)
     @param id  id of gun
     */

    public void deleteVehicleById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Vehicle vehicleToDelete = session.find(Vehicle.class, id);
        Query query = session.createQuery("from FireTest ft where ft.targetVehicle = :vehicleToDelete or ft.vehicle = :vehicleToDelete");
        query.setParameter("vehicleToDelete", vehicleToDelete);
        List<FireTest> fireTestsToDelete = query.getResultList();

        for (FireTest fireTestToDelete: fireTestsToDelete) {
            session.remove(fireTestToDelete);
        }
        session.remove(vehicleToDelete);
        session.getTransaction().commit();
        session.close();

        refreshFireTestTable();
    }

    /**
     * deletes Gun by id (propagates deletion of linked entities: FireTest, Vehicle)
    @param id id of gun
     */
    public void deleteGunById( Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Gun gunToDelete = session.find(Gun.class, id);
        Query query = session.createQuery("from Vehicle v where v.gun=:gunToDelete");
        query.setParameter("gunToDelete", gunToDelete);
        query.getResultList();
        List<Vehicle> vehiclesToDelete = query.getResultList();

        session.getTransaction().commit();
        session.close();

        for (Vehicle vehicleToDelete:vehiclesToDelete) {
            deleteVehicleById(vehicleToDelete.getId());
        }


        session = sessionFactory.openSession();
        session.beginTransaction();

        session.remove(gunToDelete);

        session.getTransaction().commit();
        session.close();

        refreshVehicleTable();
    }
    /**
     * deletes Entity by id (no delete propagation)
     * @param entityClass class object of entity to be deleted
     * @param id          id of entity
     * @param <T>         class name of entity to be deleted
     */
    public <T> void deleteEntityById(Class<T> entityClass, Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //propagate delete of certain entities

        if (entityClass.getClass().getSimpleName() == "Gun"){
            String selectionQuery = "SELECT FireTest FROM FireTest WHERE FireTest.targetVehicle = "+ id;
            List<FireTest> fireTests = (List<FireTest>) session.createQuery(selectionQuery).list();
            session.remove(fireTests);
            String selectionQuery2 = "SELECT FireTest FROM FireTest WHERE FireTest.targetVehicle = "+ id;
            List<FireTest> fireTests2 =  session.createQuery(selectionQuery2).list();
            session.remove(fireTests2);
        }else if (entityClass.getClass().getSimpleName() == "Vehicle" ){
            String selectionQuery = "SELECT FireTest FROM FireTest WHERE FireTest.targetVehicle = "+ id;
            List<FireTest> fireTests = (List<FireTest>) session.createQuery(selectionQuery).list();
            session.remove(fireTests);
            String selectionQuery2 = "SELECT FireTest FROM FireTest WHERE FireTest.targetVehicle = "+ id;
            List<FireTest> fireTests2 =  session.createQuery(selectionQuery2).list();
            session.remove(fireTests2);
        }

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