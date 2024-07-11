package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.*;
import codegym.tequila.fisioapp.model.*;
import codegym.tequila.fisioapp.repository.MedicalRecordRepository;
import codegym.tequila.fisioapp.repository.PatientRepository;
import codegym.tequila.fisioapp.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final MedicalRecordRepository medicalRecordRepository;

    public PatientServiceImpl(PatientRepository patientRepository, MedicalRecordRepository medicalRecordRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public MedicalRecordDto getMedicalRecordForPatient(String patientId) {

        patientRepository.findById(patientId).orElseThrow(() -> new NoSuchElementException("Patient " + patientId + " not found"));

        return MedicalRecordServiceImpl.convertMedicalRecordToDto(medicalRecordRepository.findByPatientId(patientId).orElseThrow(() -> new NoSuchElementException("Medical Record for Patient " + patientId + " not found")));
    }

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = new Patient();

        patient.setId(UUID.randomUUID().toString());
        patient.setName(patientDto.getName());

        patientRepository.save(patient);

        patientDto.setId(patient.getId());

        return patientDto;
    }

}
