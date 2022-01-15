package com.giga.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Hibernate mapped entity FireTest class
 *
 * @author GigaNByte
 * @since 1.0
 */

@Entity
@Table(name = "fire_tests")
public class FireTest {


    //TODO:technicaly enums should be side hull,front hull etc.
    //TODO:Implement Armor part Class or extend enum to contain thickness value

    /**
     * Enum of Implemented vehicle parts
     *
     * @author GigaNByte
     * @since 1.0
     */
    public enum VehiclePart {
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

    /**
     * Possible results of penetration test
     *
     * @author GigaNByte
     * @since 1.0
     */
    public enum TestResult {
        PENETRATION,
        NO_PENETRATION,
    }

    private Integer id;
    private String testName;
    private Date testDate;
    private Integer shotVerticalAngle;
    private Integer shotHorizontalAngle;
    private Double shotCompoundAngle;
    private Double relativeArmorThickness;
    private Integer shotDistance;
    private Vehicle vehicle;
    private Vehicle targetVehicle;
    private TestResult result;
    private VehiclePart targetVehiclePart;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "vehicle", nullable = false)
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "target_vehicle", nullable = false)
    public Vehicle getTargetVehicle() {
        return targetVehicle;
    }

    public void setTargetVehicle(Vehicle targetVehicle) {
        this.targetVehicle = targetVehicle;
    }

    @Basic(optional = false)
    @Column(name = "test_name")
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    //TODO:Implement Timestampable.java: https://stackoverflow.com/questions/8202154/how-to-create-an-auto-generated-date-timestamp-field-in-a-play-jpa
    //TODO:Or remove timestamp from app
    @Basic
    @Column(name = "test_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    @Basic
    @Column(name = "shot_vertical_angle", nullable = false)
    public Integer getShotVerticalAngle() {
        return shotVerticalAngle;
    }

    public void setShotVerticalAngle(Integer shotVerticalAngle) {
        this.shotVerticalAngle = shotVerticalAngle;
    }

    @Basic
    @Column(name = "shot_horizontal_angle", nullable = false)
    public Integer getShotHorizontalAngle() {
        return shotHorizontalAngle;
    }

    public void setShotHorizontalAngle(Integer shotHorizontalAngle) {
        this.shotHorizontalAngle = shotHorizontalAngle;
    }
    @Basic
    @Column(name = "shot_compound_angle", nullable = false)
    public Double getShotCompoundAngle() {
        return shotCompoundAngle;
    }

    public void setShotCompoundAngle(Double shotCompoundAngle) {
        this.shotCompoundAngle = shotCompoundAngle;
    }
    @Basic
    @Column(name = "relative_armor_thickness", nullable = false)
    public Double getRelativeArmorThickness() {
        return relativeArmorThickness;
    }

    public void setRelativeArmorThickness(Double relativeArmorThickness) {
        this.relativeArmorThickness = relativeArmorThickness;
    }

    @Column(name = "shot_distance", nullable = false)
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
    @Column(name = "result", nullable = false)
    public void setResult(TestResult result) {
        this.result = result;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "target_vehicle_part", nullable = false)
    public VehiclePart getTargetVehiclePart() {
        return targetVehiclePart;
    }

    public void setTargetVehiclePart(VehiclePart targetVehiclePart) {
        this.targetVehiclePart = targetVehiclePart;
    }

    public void calculateResult(){
        Context.getInstance().calculateFireTestResult(this);
    }
}
