package codegym.tequila.fisioapp.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Embedded
    private PhysicalExploration physicalExploration;

    @Embedded
    private PersonalRecords personalRecords;

    @Embedded
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(patient, that.patient) && Objects.equals(physicalExploration, that.physicalExploration) && Objects.equals(personalRecords, that.personalRecords) && Objects.equals(familiarRecords, that.familiarRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient, physicalExploration, personalRecords, familiarRecords);
    }
}
