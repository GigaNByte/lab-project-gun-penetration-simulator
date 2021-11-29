package com.giga.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class SavedTests {
    private Integer id;
    private Integer vehicleId;
    private Timestamp testDate;
    private Integer targetVehicleId;
    private Integer shotAngle;
    private String result;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "vehicle_id", nullable = false)
    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
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
    @Column(name = "target_vehicle_id", nullable = false)
    public Integer getTargetVehicleId() {
        return targetVehicleId;
    }

    public void setTargetVehicleId(Integer targetVehicleId) {
        this.targetVehicleId = targetVehicleId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavedTests that = (SavedTests) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (vehicleId != null ? !vehicleId.equals(that.vehicleId) : that.vehicleId != null) return false;
        if (testDate != null ? !testDate.equals(that.testDate) : that.testDate != null) return false;
        if (targetVehicleId != null ? !targetVehicleId.equals(that.targetVehicleId) : that.targetVehicleId != null)
            return false;
        if (shotAngle != null ? !shotAngle.equals(that.shotAngle) : that.shotAngle != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (vehicleId != null ? vehicleId.hashCode() : 0);
        result1 = 31 * result1 + (testDate != null ? testDate.hashCode() : 0);
        result1 = 31 * result1 + (targetVehicleId != null ? targetVehicleId.hashCode() : 0);
        result1 = 31 * result1 + (shotAngle != null ? shotAngle.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }
}
