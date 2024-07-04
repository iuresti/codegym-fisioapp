package codegym.tequila.fisioapp.dto;

import codegym.tequila.fisioapp.model.FamiliarRecords;
import codegym.tequila.fisioapp.model.Patient;
import codegym.tequila.fisioapp.model.PersonalRecords;
import codegym.tequila.fisioapp.model.PhysicalExploration;

import java.util.Objects;


public class MedicalRecordDto {

    private String id;
    private Patient patient;
    private PhysicalExploration physicalExploration;
    private PersonalRecords personalRecords;
    private FamiliarRecords familiarRecords;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public PhysicalExploration getPhysicalExploration() {
        return physicalExploration;
    }

    public void setPhysicalExploration(PhysicalExploration physicalExploration) {
        this.physicalExploration = physicalExploration;
    }

    public PersonalRecords getPersonalRecords() {
        return personalRecords;
    }

    public void setPersonalRecords(PersonalRecords personalRecords) {
        this.personalRecords = personalRecords;
    }

    public FamiliarRecords getFamiliarRecords() {
        return familiarRecords;
    }

    public void setFamiliarRecords(FamiliarRecords familiarRecords) {
        this.familiarRecords = familiarRecords;
    }

    @Override
    public String toString() {
        return "MedicalRecordDto{" +
                "id='" + id + '\'' +
                ", patient=" + patient +
                ", physicalExploration=" + physicalExploration +
                ", personalRecords=" + personalRecords +
                ", familiarRecords=" + familiarRecords +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecordDto that = (MedicalRecordDto) o;
        return Objects.equals(id, that.id) && Objects.equals(patient, that.patient) && Objects.equals(physicalExploration, that.physicalExploration) && Objects.equals(personalRecords, that.personalRecords) && Objects.equals(familiarRecords, that.familiarRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient, physicalExploration, personalRecords, familiarRecords);
    }
}
