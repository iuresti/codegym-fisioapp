package codegym.tequila.fisioapp.dto;


import java.util.Objects;


public class MedicalRecordDto {

    private String id;
    private PatientDto patient;
    private PhysicalExplorationDto physicalExploration;
    private PersonalRecordsDto personalRecords;
    private FamiliarRecordsDto familiarRecords;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public PhysicalExplorationDto getPhysicalExploration() {
        return physicalExploration;
    }

    public void setPhysicalExploration(PhysicalExplorationDto physicalExploration) {
        this.physicalExploration = physicalExploration;
    }

    public PersonalRecordsDto getPersonalRecords() {
        return personalRecords;
    }

    public void setPersonalRecords(PersonalRecordsDto personalRecords) {
        this.personalRecords = personalRecords;
    }

    public FamiliarRecordsDto getFamiliarRecords() {
        return familiarRecords;
    }

    public void setFamiliarRecords(FamiliarRecordsDto familiarRecords) {
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
