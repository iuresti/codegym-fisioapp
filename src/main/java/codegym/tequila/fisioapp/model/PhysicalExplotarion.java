package codegym.tequila.fisioapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "physical_exploration", schema = "fisioapp")
public class PhysicalExplotarion {

    @Id
    private String id;

    @Column(precision = 5, scale = 2)
    private Double weight;

    @Column(precision = 3, scale = 2)
    private Double height;
    @Column(name = "weekly_cardio_frequency")
    private Integer weeklyCardiofrequency;
    @Column(name = "blood_type")
    private String bloodType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getWeeklyCardiofrequency() {
        return weeklyCardiofrequency;
    }

    public void setWeeklyCardiofrequency(Integer weeklyCardiofrequency) {
        this.weeklyCardiofrequency = weeklyCardiofrequency;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}