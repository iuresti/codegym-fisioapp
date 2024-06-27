package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.model.MedicalRecord;
import codegym.tequila.fisioapp.model.Patient;
import codegym.tequila.fisioapp.repository.MedicalRecordRepository;
import codegym.tequila.fisioapp.repository.PatientRepository;
import codegym.tequila.fisioapp.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final MedicalRecordRepository medicalRecordRepository;

    public PatientServiceImpl(PatientRepository patientRepository, MedicalRecordRepository medicalRecordRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public MedicalRecord getMedicalRecordForPatient(String patientId) {

        Patient patientExists = patientRepository.findById(patientId).orElseThrow();

        return medicalRecordRepository.findByPatientId(patientId).orElseThrow();
    }

    @Override
    public Patient createPatient(Patient patient) {
        Patient usagePatient = new Patient();

        usagePatient.setId(UUID.randomUUID().toString());
        usagePatient.setName(patient.getName());

        return patientRepository.save(usagePatient);
    }
}
