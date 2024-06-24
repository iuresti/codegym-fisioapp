package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.model.MedicalRecord;
import codegym.tequila.fisioapp.repository.MedicalRecordRepository;
import codegym.tequila.fisioapp.service.MedicalRecordService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordUsage = medicalRecordRepository.findById(medicalRecord.getId()).orElseThrow();

        if (medicalRecord.getPhysicalExplotarion() != null){
            medicalRecordUsage.setPhysicalExplotarion(medicalRecord.getPhysicalExplotarion());
        }
        if (medicalRecord.getPersonalRecords() != null){
            medicalRecordUsage.setPersonalRecords(medicalRecord.getPersonalRecords());
        }
        if (medicalRecord.getFamiliarRecords() != null){
            medicalRecordUsage.setFamiliarRecords(medicalRecord.getFamiliarRecords());
        }

        return medicalRecordRepository.save(medicalRecordUsage);
    }

    @Override
    public MedicalRecord findById(String id) {
        return medicalRecordRepository.findById(id).orElseThrow();
    }

    @Override
    public MedicalRecord findByPatientId(String id) {
        return medicalRecordRepository.findByPatientId(id).orElseThrow();
    }
}
