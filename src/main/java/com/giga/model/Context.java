
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
     * Refreshes FireTest Entities from Database
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

    //TODO: Check if entity needs to be only saved or updated with associated entities (performance)
    public void saveOrUpdateEntity(Object entityObject) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(entityObject);
        session.getTransaction().commit();
        session.close();

        if (entityObject.getClass() == Vehicle.class ){
            updateFireTestsByVehicle((Vehicle) entityObject);

        }else if (entityObject.getClass() == Gun.class ) {
            updateVehiclesByGun((Gun) entityObject);
        }
    }

    /**
    * Updates All Vehicles associated with Gun object
    *
    */
    private void updateVehiclesByGun(Gun entityObject) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Vehicle v where v.gun=:updatedGun");
        query.setParameter("updatedGun", entityObject);
        List<Vehicle> vehiclesToUpdate = query.getResultList();
        session.getTransaction().commit();
        session.close();

        for (Vehicle vehicleToUpdate: vehiclesToUpdate) {
            updateFireTestsByVehicle(vehicleToUpdate);
        }
    }
    /**
     * Updates All Firetests (recalculates penetration result) from associated with Vehicle object
     *
     */

    public void updateFireTestsByVehicle(Vehicle  updatedVehicle){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from FireTest ft where ft.targetVehicle = :updatedVehicleId or ft.vehicle = :updatedVehicleId");
        query.setParameter("updatedVehicleId",updatedVehicle);
        List<FireTest> fireTestsToUpdate= query.getResultList();
        session.getTransaction().commit();
        session.close();
        for (FireTest fireTestToUpdate : fireTestsToUpdate) {
            calculateAndUpdateFireTestResult(fireTestToUpdate);
        }
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
     * Refreshes VehicleTable Entities from Database
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
     * deletes Vehicle by id (propagates deletion of linked entities: FireTest)
     *
     * @param id id of gun
     */

    public void deleteVehicleById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Vehicle vehicleToDelete = session.find(Vehicle.class, id);
        Query query = session.createQuery("from FireTest ft where ft.targetVehicle = :vehicleToDelete or ft.vehicle = :vehicleToDelete");
        query.setParameter("vehicleToDelete", vehicleToDelete);
        List<FireTest> fireTestsToDelete = query.getResultList();

        for (FireTest fireTestToDelete : fireTestsToDelete) {
            session.remove(fireTestToDelete);
        }
        session.remove(vehicleToDelete);
        session.getTransaction().commit();
        session.close();

        refreshFireTestTable();
    }



    public void deleteGunById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Gun gunToDelete = session.find(Gun.class, id);
        Query query = session.createQuery("from Vehicle v where v.gun=:gunToDelete");
        query.setParameter("gunToDelete", gunToDelete);
        query.getResultList();
        List<Vehicle> vehiclesToDelete = query.getResultList();

        session.getTransaction().commit();
        session.close();

        for (Vehicle vehicleToDelete : vehiclesToDelete) {
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
     *
     * @param entityClass class object of entity to be deleted
     * @param id          id of entity
     * @param <T>         class name of entity to be deleted
     */
    public <T> void deleteEntityById(Class<T> entityClass, Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //propagate delete of certain entities

        if (entityClass.getClass().getSimpleName() == "Gun") {
            String selectionQuery = "SELECT FireTest FROM FireTest WHERE FireTest.targetVehicle = " + id;
            List<FireTest> fireTests = (List<FireTest>) session.createQuery(selectionQuery).list();
            session.remove(fireTests);
            String selectionQuery2 = "SELECT FireTest FROM FireTest WHERE FireTest.targetVehicle = " + id;
            List<FireTest> fireTests2 = session.createQuery(selectionQuery2).list();
            session.remove(fireTests2);
        } else if (entityClass.getClass().getSimpleName() == "Vehicle") {
            String selectionQuery = "SELECT FireTest FROM FireTest WHERE FireTest.targetVehicle = " + id;
            List<FireTest> fireTests = (List<FireTest>) session.createQuery(selectionQuery).list();
            session.remove(fireTests);
            String selectionQuery2 = "SELECT FireTest FROM FireTest WHERE FireTest.targetVehicle = " + id;
            List<FireTest> fireTests2 = session.createQuery(selectionQuery2).list();
            session.remove(fireTests2);
        }

        T entity = session.find(entityClass, id);
        session.remove(entity);


        session.getTransaction().commit();
        session.close();
    }

    /**
     * @param entityClass Class Object instance of entity
     * @param id          id of entity
     * @param <T>         Object Type of Entity
     * @return entity by id
     */
    public <T> T getEntityById(Class<T> entityClass, Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        T entity = session.find(entityClass, id);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    /**
     * Calculates and updates in db test result of Firetest by Id
     *
     * @param fireTest Firetest Object
     */
    private void calculateAndUpdateFireTestResult(FireTest fireTest) {
        calculateFireTestResult(fireTest);
        saveOrUpdateEntity(fireTest);
    }

    /**
     * Calculates test result of Firetest Object
     *
     * @param firetest Firetest Object
     */

    public void calculateFireTestResult(FireTest firetest) {
        Double armorThickness = 0d;
        Double effectiveArmorThickness = 0d;
        Double absoluteShotAngle = 0d;
        Integer rhaGunPenetration = 0;

        //TODO:Implement Armor part Class or extend enum to contain thickness value
        //TODO:Implement second Shot angle variable (vertical angle)
        //calculates effective armor thickness
        if (firetest.getTargetVehiclePart() == FireTest.VehiclePart.FRONT_ARMOR) {
            armorThickness = Double.valueOf(firetest.getTargetVehicle().getFrontArmorThickness());
            absoluteShotAngle = (double) (firetest.getTargetVehicle().getFrontArmorAngle() - firetest.getShotAngle());

        } else if (firetest.getTargetVehiclePart() == FireTest.VehiclePart.SIDE_ARMOR) {
            armorThickness = Double.valueOf(firetest.getTargetVehicle().getSideArmorThickness());
            absoluteShotAngle = (double) (firetest.getTargetVehicle().getSideArmorAngle() - firetest.getShotAngle());
        }

        if (absoluteShotAngle > 0) {
            effectiveArmorThickness = armorThickness / Math.sin(Math.toRadians(Math.abs(absoluteShotAngle)));
        } else if ((absoluteShotAngle < 0)) {
            effectiveArmorThickness = armorThickness / Math.cos(Math.toRadians(Math.abs(absoluteShotAngle)));
        }else{
            effectiveArmorThickness = armorThickness;
        }

        //TODO:Implement "Shell Critical Bounce Angle" variable dependent from ammo type
        //TODO: Penetration values could be stored here as Map

        switch (firetest.getShotDistance()) {
            case 100:
                rhaGunPenetration = firetest.getVehicle().getGun().getPen100();
                break;
            case 300:
                rhaGunPenetration = firetest.getVehicle().getGun().getPen300();
                break;
            case 500:
                rhaGunPenetration = firetest.getVehicle().getGun().getPen500();
            case 1000:
                rhaGunPenetration = firetest.getVehicle().getGun().getPen1000();
                break;
            case 1500:
                rhaGunPenetration = firetest.getVehicle().getGun().getPen1500();
                break;
            case 2000:
                rhaGunPenetration = firetest.getVehicle().getGun().getPen2000();
                break;
            case 2500:
                rhaGunPenetration = firetest.getVehicle().getGun().getPen2500();
                break;
            case 3000:
                rhaGunPenetration = firetest.getVehicle().getGun().getPen3000();
                break;
            default:
                // code block
        }

        System.out.println("=========Firetest========");
        System.out.println("actual: " + armorThickness);
        System.out.printf("efective: %f\n", effectiveArmorThickness);
        System.out.println("abs angle: " + absoluteShotAngle);
        System.out.println("shot angle" + firetest.getShotAngle());
        System.out.println("armor angle" + firetest.getTargetVehicle().getFrontArmorAngle());
        System.out.println("gun penetration: " + rhaGunPenetration);
        System.out.println("=========================");

        if (rhaGunPenetration >= effectiveArmorThickness) {
            firetest.setResult(FireTest.TestResult.PENETRATION);

        } else {
            firetest.setResult(FireTest.TestResult.NO_PENETRATION);
        }


    }


    private final static Context instance = new Context();

    /**
     * @return singleton instance of Context object
     */
    public static Context getInstance() {
        return instance;
    }


}