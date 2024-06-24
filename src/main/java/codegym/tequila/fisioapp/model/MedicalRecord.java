package codegym.tequila.fisioapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medical_record", schema = "fisioapp")
public class MedicalRecord {

    @Id
    private String id;

    //TODO: conectar patient a medical record con mappedBy
    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "physical_exploration_id")
    private PhysicalExplotarion physicalExplotarion;

    @OneToOne
    @JoinColumn(name = "personal_records_id")
    private PersonalRecords personalRecords;

    @OneToOne
    @JoinColumn(name = "familiar_records_id")
    private FamiliarRecords familiarRecords;

}
