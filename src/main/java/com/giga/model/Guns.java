package com.giga.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Guns {
    private Integer id;
    private String gunName;
    private String ammoName;
    private String nation;
    private String ammoType;
    private Integer barrelLenght;
    private Integer caliber;
    private Integer muzzleVelocity;
    private Integer pen100;
    private Integer pen300;
    private Integer pen500;
    private Integer pen1000;
    private Integer pen1500;
    private Integer pen2000;
    private Integer pen3000;

    @Id
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
    public Integer getBarrelLenght() {
        return barrelLenght;
    }

    public void setBarrelLenght(Integer barrelLenght) {
        this.barrelLenght = barrelLenght;
    }

    @Basic
    @Column(name = "caliber", nullable = false)
    public Integer getCaliber() {
        return caliber;
    }

    public void setCaliber(Integer caliber) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guns guns = (Guns) o;

        if (id != null ? !id.equals(guns.id) : guns.id != null) return false;
        if (gunName != null ? !gunName.equals(guns.gunName) : guns.gunName != null) return false;
        if (ammoName != null ? !ammoName.equals(guns.ammoName) : guns.ammoName != null) return false;
        if (nation != null ? !nation.equals(guns.nation) : guns.nation != null) return false;
        if (ammoType != null ? !ammoType.equals(guns.ammoType) : guns.ammoType != null) return false;
        if (barrelLenght != null ? !barrelLenght.equals(guns.barrelLenght) : guns.barrelLenght != null) return false;
        if (caliber != null ? !caliber.equals(guns.caliber) : guns.caliber != null) return false;
        if (muzzleVelocity != null ? !muzzleVelocity.equals(guns.muzzleVelocity) : guns.muzzleVelocity != null)
            return false;
        if (pen100 != null ? !pen100.equals(guns.pen100) : guns.pen100 != null) return false;
        if (pen300 != null ? !pen300.equals(guns.pen300) : guns.pen300 != null) return false;
        if (pen500 != null ? !pen500.equals(guns.pen500) : guns.pen500 != null) return false;
        if (pen1000 != null ? !pen1000.equals(guns.pen1000) : guns.pen1000 != null) return false;
        if (pen1500 != null ? !pen1500.equals(guns.pen1500) : guns.pen1500 != null) return false;
        if (pen2000 != null ? !pen2000.equals(guns.pen2000) : guns.pen2000 != null) return false;
        if (pen3000 != null ? !pen3000.equals(guns.pen3000) : guns.pen3000 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (gunName != null ? gunName.hashCode() : 0);
        result = 31 * result + (ammoName != null ? ammoName.hashCode() : 0);
        result = 31 * result + (nation != null ? nation.hashCode() : 0);
        result = 31 * result + (ammoType != null ? ammoType.hashCode() : 0);
        result = 31 * result + (barrelLenght != null ? barrelLenght.hashCode() : 0);
        result = 31 * result + (caliber != null ? caliber.hashCode() : 0);
        result = 31 * result + (muzzleVelocity != null ? muzzleVelocity.hashCode() : 0);
        result = 31 * result + (pen100 != null ? pen100.hashCode() : 0);
        result = 31 * result + (pen300 != null ? pen300.hashCode() : 0);
        result = 31 * result + (pen500 != null ? pen500.hashCode() : 0);
        result = 31 * result + (pen1000 != null ? pen1000.hashCode() : 0);
        result = 31 * result + (pen1500 != null ? pen1500.hashCode() : 0);
        result = 31 * result + (pen2000 != null ? pen2000.hashCode() : 0);
        result = 31 * result + (pen3000 != null ? pen3000.hashCode() : 0);
        return result;
    }
}
