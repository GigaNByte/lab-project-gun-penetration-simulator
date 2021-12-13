package com.giga;

import com.giga.model.Gun;
import com.giga.model.SavedTest;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//testing
public class AppTest {

    @Test
    public void app_insert_delete_test() {
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

        Instant now = Instant.now();
        System.out.println(now);
        Timestamp current = Timestamp.from(now);

        SavedTest testSavedTest = new SavedTest();
        testSavedTest.setTestDate(current);
        testSavedTest.setResult("Penetration");
        testSavedTest.setShotAngle(0);

        // should it be testVehicle ?
        // one test one vehicle many tests can have many (non-unique) vehicles

        testSavedTest.setTargetVehicle(testVehicle);
        testSavedTest.setVehicle(testVehicle);


        SavedTest testSavedTest2 = new SavedTest();
        testSavedTest2.setTestDate(current);
        testSavedTest2.setResult("Penetration");
        testSavedTest2.setShotAngle(10);
        testSavedTest2.setTargetVehicle(testVehicle);
        testSavedTest2.setVehicle(testVehicle2);



        SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(testGun);
        session.save(testGun2);
        session.getTransaction().commit();
        session.close();

        sessionFactory = HibernateConnection.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        Assert.assertEquals(2L, session.createQuery("SELECT COUNT(*) FROM Gun").getSingleResult());
        session.getTransaction().commit();
        session.close();
        /*
        session.persist(testVehicle);
        session.persist(testVehicle2);
        session.persist(testSavedTest2);
        session.persist(testSavedTest);

        //count default entries


        Assert.assertEquals(2L, session.createQuery("SELECT COUNT(*) FROM SavedTest").getSingleResult());
        Assert.assertEquals(2L, session.createQuery("SELECT COUNT(*) FROM Vehicle ").getSingleResult());
        Assert.assertEquals(2L, session.createQuery("SELECT COUNT(*) FROM Gun").getSingleResult());


        SavedTest result = (SavedTest) session.createQuery("FROM SavedTest").setMaxResults(1).getSingleResult();


        //propagate deleting of single vehicle without gun entity

        session.remove(testVehicle);

        Assert.assertEquals(1L, session.createQuery("SELECT COUNT(*) FROM Vehicle ").getSingleResult());
        Assert.assertEquals(2L, session.createQuery("SELECT COUNT(*) FROM Gun").getSingleResult());
        //TODO:"DELETE FROM vehicles ..." should delete all saved tests associated with deleted testVehicle
       // Assert.assertEquals(0L, session.createQuery("SELECT COUNT(*) FROM SavedTest").getSingleResult());

        session.close();
*/
    }
}
