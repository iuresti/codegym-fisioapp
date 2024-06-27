package codegym.tequila.fisioapp.repository;

import codegym.tequila.fisioapp.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, String> {
    Optional<MedicalRecord> findByPatientId(String patientId);
}