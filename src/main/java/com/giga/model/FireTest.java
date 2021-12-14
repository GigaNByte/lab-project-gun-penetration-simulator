package com.giga.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="fire_tests")
public class FireTest {



    //technicaly enums should be side hull,front hull etc.
    public enum  VehiclePart{
        FRONT_ARMOR("Front Hull"),
        SIDE_ARMOR("Side Hull");

        private final String text;

        VehiclePart(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public enum  TestResult{
        PENETRATION,
        NO_PENETRATION,
    }

    private Integer id;
    private String testName;
    private Timestamp testDate;
    private Integer shotAngle;
    private Integer shotDistance;
    private Vehicle vehicle;
    private Vehicle targetVehicle;
    private TestResult result;
    private VehiclePart targetVehiclePart;


    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="vehicle", nullable=false)
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="targetVehicle", nullable=false)
    public Vehicle getTargetVehicle() {
        return targetVehicle;
    }

    public void setTargetVehicle(Vehicle targetVehicle) {
        this.targetVehicle = targetVehicle;
    }

    @Basic
    @Column(name = "test_name")
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    @Basic
    @Column(name = "test_date", nullable = false)
    public Timestamp getTestDate() {
        return testDate;
    }

    public void setTestDate(Timestamp testDate) {
        this.testDate = testDate;
    }

    @Basic
    @Column(name = "shot_angle", nullable = false)
    public Integer getShotAngle() {
        return shotAngle;
    }

    public void setShotAngle(Integer shotAngle) {
        this.shotAngle = shotAngle;
    }
    @Column(name="shotDistance", nullable = false)
    public Integer getShotDistance() {
        return shotDistance;
    }

    public void setShotDistance(Integer shotDistance) {
        this.shotDistance = shotDistance;
    }

    public TestResult getResult() {
        return result;
    }
    @Enumerated(EnumType.STRING)
    @Column(name="result", nullable = false)
    public void setResult(TestResult result) {
        this.result = result;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="targetVehiclePart", nullable = false)
    public VehiclePart getTargetVehiclePart() {
        return targetVehiclePart;
    }

    public void setTargetVehiclePart(VehiclePart targetVehiclePart) {
        this.targetVehiclePart = targetVehiclePart;
    }

}
