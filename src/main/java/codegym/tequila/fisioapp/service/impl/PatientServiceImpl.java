package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.MedicalRecordDto;
import codegym.tequila.fisioapp.dto.PatientDto;
import codegym.tequila.fisioapp.model.MedicalRecord;
import codegym.tequila.fisioapp.model.Patient;
import codegym.tequila.fisioapp.repository.MedicalRecordRepository;
import codegym.tequila.fisioapp.repository.PatientRepository;
import codegym.tequila.fisioapp.service.PatientService;
import org.springframework.beans.BeanUtils;
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

    public MedicalRecordDto getMedicalRecordForPatient(String patientId) {

        Patient patientExists = patientRepository.findById(patientId).orElseThrow();

        return convertMedicalRecordToDto(medicalRecordRepository.findByPatientId(patientId).orElseThrow());
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

    private static PatientDto convertPatientToDto (Patient patient){
        PatientDto patientDto = new PatientDto();

        BeanUtils.copyProperties(patient, patientDto);

        return patientDto;
    }
    private static MedicalRecordDto convertMedicalRecordToDto(MedicalRecord medicalRecord) {
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();

        BeanUtils.copyProperties(medicalRecord, medicalRecordDto);

        return medicalRecordDto;
    }
}
