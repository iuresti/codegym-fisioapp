package codegym.tequila.fisioapp.dto;

import java.util.Objects;

public class PhysicalExplorationDto {
    private Double weight;
    private Double height;
    private Integer weeklyCardioFrequency;
    private String bloodType;

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

    public Integer getWeeklyCardioFrequency() {
        return weeklyCardioFrequency;
    }

    public void setWeeklyCardioFrequency(Integer weeklyCardioFrequency) {
        this.weeklyCardioFrequency = weeklyCardioFrequency;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    @Override
    public String toString() {
        return "PhysicalExplorationDto{" +
                "weight=" + weight +
                ", height=" + height +
                ", weeklyCardioFrequency=" + weeklyCardioFrequency +
                ", bloodType='" + bloodType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalExplorationDto that = (PhysicalExplorationDto) o;
        return Objects.equals(weight, that.weight) && Objects.equals(height, that.height) && Objects.equals(weeklyCardioFrequency, that.weeklyCardioFrequency) && Objects.equals(bloodType, that.bloodType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, height, weeklyCardioFrequency, bloodType);
    }
}
