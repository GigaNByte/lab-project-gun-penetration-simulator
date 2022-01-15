package com.giga;

import com.giga.model.Gun;
import com.giga.model.FireTest;
import com.giga.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.Assert;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AppTest {
    //TODO delete on cascade doesn't work:https://stackoverflow.com/questions/14875793/jpa-hibernate-how-to-define-a-constraint-having-on-delete-cascade
    @Test
    public void app_delete_propragation() {
        //add gun and vehicle and assign them to two fire tests
        Gun testGun = new Gun();
        testGun.setGunName("76.2mm/L30.5 (L-11)");
        testGun.setAmmoName("BR-350A");
        testGun.setAmmoType("APBC");
        testGun.setBarrelLenght(3162.);
        testGun.setCaliber(76.2);
        testGun.setMuzzleVelocity(335);
        testGun.setNation("USSR");
        testGun.setPen100(114);

        Gun testGun2 = new Gun();
        testGun2.setGunName("85mm/L52 (D-5T)");
        testGun2.setAmmoName("BR-365K");
        testGun2.setAmmoType("AP");
        testGun2.setBarrelLenght(4420.);
        testGun2.setCaliber(85.);
        testGun2.setMuzzleVelocity(792);
        testGun2.setNation("USSR");
        testGun2.setPen100(127);

        Vehicle testVehicle = new Vehicle();
        testVehicle.setVehicleName("KW-1");
        testVehicle.setNation("USSR");
        testVehicle.setFrontArmorAngle(31);
        testVehicle.setFrontArmorThickness(70);
        testVehicle.setSideArmorThickness(75);
        testVehicle.setSideArmorAngle(0);
        testVehicle.setGun(testGun);

        Vehicle testVehicle2 = new Vehicle();
        testVehicle2.setVehicleName("T-34-85");
        testVehicle2.setNation("USSR");
        testVehicle2.setFrontArmorAngle(60);
        testVehicle2.setFrontArmorThickness(45);
        testVehicle2.setSideArmorThickness(45);
        testVehicle2.setSideArmorAngle(40);
        testVehicle2.setGun(testGun2);

        Date date = new Date();
        FireTest testFireTest = new FireTest();
        testFireTest.setTestName("test1");
        testFireTest.setResult(FireTest.TestResult.PENETRATION);
        testFireTest.setShotVerticalAngle(0);
        testFireTest.setShotDistance(10);
        testFireTest.setTargetVehicle(testVehicle);
        testFireTest.setVehicle(testVehicle2);
        testFireTest.setTargetVehiclePart(FireTest.VehiclePart.FRONT_ARMOR);
        testFireTest.setTestDate(date);

        FireTest testFireTest2 = new FireTest();
        testFireTest2.setTestName("test2");
        testFireTest2.setResult(FireTest.TestResult.PENETRATION);
        testFireTest2.setShotVerticalAngle(10);
        testFireTest2.setShotDistance(10);
        testFireTest2.setTargetVehicle(testVehicle2);
        testFireTest2.setVehicle(testVehicle);
        testFireTest2.setTargetVehiclePart(FireTest.VehiclePart.FRONT_ARMOR);
        testFireTest2.setTestDate(date);


        SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
        sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(testGun2);
        session.persist(testGun);
        session.persist(testVehicle);
        session.persist(testVehicle2);
        session.persist(testFireTest2);
        session.persist(testFireTest);
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        Assert.assertEquals(2L, session.createQuery("SELECT COUNT(*) FROM Gun").getSingleResult());
        Assert.assertEquals(2L, session.createQuery("SELECT COUNT(*) FROM Vehicle").getSingleResult());
        Assert.assertEquals(2L, session.createQuery("SELECT COUNT(*) FROM FireTest").getSingleResult());
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(testVehicle);
        session.getTransaction().commit();
        session.close();


        //Vehicle "testVehicle" was deleted
        //testVehicle was assigned to firetest2 and firetest
        //after deleting "testVehicle", table FireTest should contain no records

        session = sessionFactory.openSession();
        session.beginTransaction();
        Assert.assertEquals(1L, session.createQuery("SELECT COUNT(*) FROM Vehicle ").getSingleResult());
        Assert.assertEquals(2L, session.createQuery("SELECT COUNT(*) FROM Gun").getSingleResult());
        Assert.assertEquals(0L, session.createQuery("SELECT COUNT(*) FROM FireTest").getSingleResult());
        session.remove(testVehicle);
        session.getTransaction().commit();
        session.close();

    }
}
