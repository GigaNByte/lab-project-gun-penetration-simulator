package com.giga.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="guns")
public class Gun {
    private Integer id;
    private String gunName;
    private String ammoName;
    private String nation;
    private String ammoType;
    private Double barrelLenght;
    private Double caliber;
    private Integer muzzleVelocity;
    private Integer pen100;
    private Integer pen300;
    private Integer pen500;
    private Integer pen1000;
    private Integer pen1500;
    private Integer pen2000;
    private Integer pen3000;
    private Set<Vehicle> vehicles;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "gun_name", nullable = false, length = 100)
    public String getGunName() {
        return gunName;
    }

    public void setGunName(String gunName) {
        this.gunName = gunName;
    }

    @Basic
    @Column(name = "ammo_name", nullable = false, length = 45)
    public String getAmmoName() {
        return ammoName;
    }

    public void setAmmoName(String ammoName) {
        this.ammoName = ammoName;
    }

    @Basic
    @Column(name = "nation", nullable = false, length = 20)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "ammo_type", nullable = false, length = 45)
    public String getAmmoType() {
        return ammoType;
    }

    public void setAmmoType(String ammoType) {
        this.ammoType = ammoType;
    }

    @Basic
    @Column(name = "barrel_lenght", nullable = false)
    public Double getBarrelLenght() {
        return barrelLenght;
    }

    public void setBarrelLenght(Double barrelLenght) {
        this.barrelLenght = barrelLenght;
    }

    @Basic
    @Column(name = "caliber", nullable = false)
    public Double getCaliber() {
        return caliber;
    }

    public void setCaliber(Double caliber) {
        this.caliber = caliber;
    }

    @Basic
    @Column(name = "muzzle_velocity", nullable = false)
    public Integer getMuzzleVelocity() {
        return muzzleVelocity;
    }

    public void setMuzzleVelocity(Integer muzzleVelocity) {
        this.muzzleVelocity = muzzleVelocity;
    }

    @Basic
    @Column(name = "pen_100", nullable = true)
    public Integer getPen100() {
        return pen100;
    }

    public void setPen100(Integer pen100) {
        this.pen100 = pen100;
    }

    @Basic
    @Column(name = "pen_300", nullable = true)
    public Integer getPen300() {
        return pen300;
    }

    public void setPen300(Integer pen300) {
        this.pen300 = pen300;
    }

    @Basic
    @Column(name = "pen_500", nullable = true)
    public Integer getPen500() {
        return pen500;
    }

    public void setPen500(Integer pen500) {
        this.pen500 = pen500;
    }

    @Basic
    @Column(name = "pen_1000", nullable = true)
    public Integer getPen1000() {
        return pen1000;
    }

    public void setPen1000(Integer pen1000) {
        this.pen1000 = pen1000;
    }

    @Basic
    @Column(name = "pen_1500", nullable = true)
    public Integer getPen1500() {
        return pen1500;
    }

    public void setPen1500(Integer pen1500) {
        this.pen1500 = pen1500;
    }

    @Basic
    @Column(name = "pen_2000", nullable = true)
    public Integer getPen2000() {
        return pen2000;
    }

    public void setPen2000(Integer pen2000) {
        this.pen2000 = pen2000;
    }

    @Basic
    @Column(name = "pen_3000", nullable = true)
    public Integer getPen3000() {
        return pen3000;
    }

    public void setPen3000(Integer pen3000) {
        this.pen3000 = pen3000;
    }

    @OneToMany (mappedBy = "gun")
    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    @Override
    public String toString(){
        return this.getGunName()+" "+this.getAmmoType()+" "+this.getAmmoName();
    }
}
