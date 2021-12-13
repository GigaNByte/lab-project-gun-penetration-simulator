package com.giga.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="vehicles")
public class Vehicle {
    private Integer id;
    private String vehicleName;
    private String nation;
    private Integer frontArmorThickness;
    private Integer sideArmorThickness;
    private Integer frontArmorAngle;
    private Integer sideArmorAngle;
    private Gun gun;
    private Set<SavedTest> saved_tests_vehicle = new HashSet<>();
    private Set<SavedTest> saved_tests_target_vehicle = new HashSet<>();



    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "vehicle")
    public Set<SavedTest> getSaved_tests_vehicle() {
        return saved_tests_vehicle;
    }

    public void setSaved_tests_vehicle(Set<SavedTest> saved_tests_vehicle) {
        this.saved_tests_vehicle = saved_tests_vehicle;
    }

    @OneToMany(mappedBy = "targetVehicle")
    public Set<SavedTest> getSaved_tests_target_vehicle() {
        return saved_tests_target_vehicle;
    }

    public void setSaved_tests_target_vehicle(Set<SavedTest> saved_tests_target_vehicle) {
        this.saved_tests_target_vehicle = saved_tests_target_vehicle;
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

    @ManyToOne
    @JoinColumn(name="gun", nullable=false)
    public Gun getGun() {
        return gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }



}
