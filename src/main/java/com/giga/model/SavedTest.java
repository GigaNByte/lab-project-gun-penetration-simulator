package com.giga.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="saved_tests")
public class SavedTest {
    private Integer id;
    private Integer vehicleId;
    private Timestamp testDate;
    private Integer shotAngle;
    private String result;
    private Vehicle vehicle;
    private Vehicle targetVehicle;
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

    @Basic
    @Column(name = "result", nullable = false, length = 45)
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
