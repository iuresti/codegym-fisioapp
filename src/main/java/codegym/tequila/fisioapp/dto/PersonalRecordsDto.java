package codegym.tequila.fisioapp.dto;

import java.util.Objects;

public class PersonalRecordsDto {

    private String chronicDiseases;

    private String previousSurgeriesAndHospitalizations;

    private String medicines;

    private String allergies;


    private String neurologicalConditions;


    private String cardiovascularConditions;


    private String respiratoryConditions;


    private String musculoskeletalConditions;

    public String getChronicDiseases() {
        return chronicDiseases;
    }

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public String getPreviousSurgeriesAndHospitalizations() {
        return previousSurgeriesAndHospitalizations;
    }

    public void setPreviousSurgeriesAndHospitalizations(String previousSurgeriesAndHospitalizations) {
        this.previousSurgeriesAndHospitalizations = previousSurgeriesAndHospitalizations;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getNeurologicalConditions() {
        return neurologicalConditions;
    }

    public void setNeurologicalConditions(String neurologicalConditions) {
        this.neurologicalConditions = neurologicalConditions;
    }

    public String getCardiovascularConditions() {
        return cardiovascularConditions;
    }

    public void setCardiovascularConditions(String cardiovascularConditions) {
        this.cardiovascularConditions = cardiovascularConditions;
    }

    public String getRespiratoryConditions() {
        return respiratoryConditions;
    }

    public void setRespiratoryConditions(String respiratoryConditions) {
        this.respiratoryConditions = respiratoryConditions;
    }

    public String getMusculoskeletalConditions() {
        return musculoskeletalConditions;
    }

    public void setMusculoskeletalConditions(String musculoskeletalConditions) {
        this.musculoskeletalConditions = musculoskeletalConditions;
    }

    @Override
    public String toString() {
        return "PersonalRecordsDto{" +
                "chronicDiseases='" + chronicDiseases + '\'' +
                ", previousSurgeriesAndHospitalizations='" + previousSurgeriesAndHospitalizations + '\'' +
                ", medicines='" + medicines + '\'' +
                ", allergies='" + allergies + '\'' +
                ", neurologicalConditions='" + neurologicalConditions + '\'' +
                ", cardiovascularConditions='" + cardiovascularConditions + '\'' +
                ", respiratoryConditions='" + respiratoryConditions + '\'' +
                ", musculoskeletalConditions='" + musculoskeletalConditions + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalRecordsDto that = (PersonalRecordsDto) o;
        return Objects.equals(chronicDiseases, that.chronicDiseases) && Objects.equals(previousSurgeriesAndHospitalizations, that.previousSurgeriesAndHospitalizations) && Objects.equals(medicines, that.medicines) && Objects.equals(allergies, that.allergies) && Objects.equals(neurologicalConditions, that.neurologicalConditions) && Objects.equals(cardiovascularConditions, that.cardiovascularConditions) && Objects.equals(respiratoryConditions, that.respiratoryConditions) && Objects.equals(musculoskeletalConditions, that.musculoskeletalConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chronicDiseases, previousSurgeriesAndHospitalizations, medicines, allergies, neurologicalConditions, cardiovascularConditions, respiratoryConditions, musculoskeletalConditions);
    }
}
