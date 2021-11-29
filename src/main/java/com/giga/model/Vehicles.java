package com.giga.model;

import javax.persistence.*;

@Entity

public class Vehicles {
    private Integer id;
    private String vehicleName;
    private String nation;
    private Integer frontArmorThickness;
    private Integer sideArmorThickness;
    private Integer frontArmorAngle;
    private Integer sideArmorAngle;
    private Integer gunId;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "vehicle_name", nullable = false, length = 100)
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Basic
    @Column(name = "nation", nullable = false, length = 45)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "front_armor_thickness", nullable = false)
    public Integer getFrontArmorThickness() {
        return frontArmorThickness;
    }

    public void setFrontArmorThickness(Integer frontArmorThickness) {
        this.frontArmorThickness = frontArmorThickness;
    }

    @Basic
    @Column(name = "side_armor_thickness", nullable = false)
    public Integer getSideArmorThickness() {
        return sideArmorThickness;
    }

    public void setSideArmorThickness(Integer sideArmorThickness) {
        this.sideArmorThickness = sideArmorThickness;
    }

    @Basic
    @Column(name = "front_armor_angle", nullable = false)
    public Integer getFrontArmorAngle() {
        return frontArmorAngle;
    }

    public void setFrontArmorAngle(Integer frontArmorAngle) {
        this.frontArmorAngle = frontArmorAngle;
    }

    @Basic
    @Column(name = "side_armor_angle", nullable = false)
    public Integer getSideArmorAngle() {
        return sideArmorAngle;
    }

    public void setSideArmorAngle(Integer sideArmorAngle) {
        this.sideArmorAngle = sideArmorAngle;
    }

    @Column(name = "gun_id", nullable = false)
    public Integer getGunId() {
        return gunId;
    }

    public void setGunId(Integer gunId) {
        this.gunId = gunId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicles vehicles = (Vehicles) o;

        if (id != null ? !id.equals(vehicles.id) : vehicles.id != null) return false;
        if (vehicleName != null ? !vehicleName.equals(vehicles.vehicleName) : vehicles.vehicleName != null)
            return false;
        if (nation != null ? !nation.equals(vehicles.nation) : vehicles.nation != null) return false;
        if (frontArmorThickness != null ? !frontArmorThickness.equals(vehicles.frontArmorThickness) : vehicles.frontArmorThickness != null)
            return false;
        if (sideArmorThickness != null ? !sideArmorThickness.equals(vehicles.sideArmorThickness) : vehicles.sideArmorThickness != null)
            return false;
        if (frontArmorAngle != null ? !frontArmorAngle.equals(vehicles.frontArmorAngle) : vehicles.frontArmorAngle != null)
            return false;
        if (sideArmorAngle != null ? !sideArmorAngle.equals(vehicles.sideArmorAngle) : vehicles.sideArmorAngle != null)
            return false;
        if (gunId != null ? !gunId.equals(vehicles.gunId) : vehicles.gunId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (vehicleName != null ? vehicleName.hashCode() : 0);
        result = 31 * result + (nation != null ? nation.hashCode() : 0);
        result = 31 * result + (frontArmorThickness != null ? frontArmorThickness.hashCode() : 0);
        result = 31 * result + (sideArmorThickness != null ? sideArmorThickness.hashCode() : 0);
        result = 31 * result + (frontArmorAngle != null ? frontArmorAngle.hashCode() : 0);
        result = 31 * result + (sideArmorAngle != null ? sideArmorAngle.hashCode() : 0);
        result = 31 * result + (gunId != null ? gunId.hashCode() : 0);
        return result;
    }
}
