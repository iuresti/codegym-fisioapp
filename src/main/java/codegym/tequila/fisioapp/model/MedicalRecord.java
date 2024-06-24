package codegym.tequila.fisioapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medical_record", schema = "fisioapp")
public class MedicalRecord {

    @Id
    private String id;

    //TODO: conectar patient a medical record con mappedBy y crear init
//    @OneToOne(mappedBy = "patient")
//    private MedicalRecord medicalRecord;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "physical_exploration_id")
    private PhysicalExplotarion physicalExplotarion;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "personal_records_id")
    private PersonalRecords personalRecords;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "familiar_records_id")
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

    public PhysicalExplotarion getPhysicalExplotarion() {
        return physicalExplotarion;
    }

    public void setPhysicalExplotarion(PhysicalExplotarion physicalExplotarion) {
        this.physicalExplotarion = physicalExplotarion;
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
}
