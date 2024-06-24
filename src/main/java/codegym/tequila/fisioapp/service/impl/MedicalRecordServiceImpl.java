package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.model.*;
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

        PersonalRecords usagePersonalRecords = medicalRecordUsage.getPersonalRecords();
        PersonalRecords updatePersonalRecords = medicalRecord.getPersonalRecords();

        if (updatePersonalRecords != null) {
            if (!updatePersonalRecords.getChronicDiseases().isEmpty()) {
                usagePersonalRecords.setChronicDiseases(updatePersonalRecords.getChronicDiseases());
            }
            if (!updatePersonalRecords.getPreviousSurgeriesAndHospitalizations().isEmpty()) {
                usagePersonalRecords.setPreviousSurgeriesAndHospitalizations(updatePersonalRecords.getPreviousSurgeriesAndHospitalizations());
            }
            if (!updatePersonalRecords.getMedicines().isEmpty()) {
                usagePersonalRecords.setMedicines(updatePersonalRecords.getMedicines());
            }
            if (!updatePersonalRecords.getAllergies().isEmpty()) {
                usagePersonalRecords.setAllergies(updatePersonalRecords.getAllergies());
            }
            if (!updatePersonalRecords.getNeurologicalConditions().isEmpty()) {
                usagePersonalRecords.setNeurologicalConditions(updatePersonalRecords.getNeurologicalConditions());
            }
            if (!updatePersonalRecords.getCardiovascularConditions().isEmpty()) {
                usagePersonalRecords.setCardiovascularConditions(updatePersonalRecords.getCardiovascularConditions());
            }
            if (!updatePersonalRecords.getRespiratoryConditions().isEmpty()) {
                usagePersonalRecords.setRespiratoryConditions(updatePersonalRecords.getRespiratoryConditions());
            }
            if (!updatePersonalRecords.getMusculoskeletalConditions().isEmpty()) {
                usagePersonalRecords.setMusculoskeletalConditions(updatePersonalRecords.getMusculoskeletalConditions());
            }
            medicalRecordUsage.setPersonalRecords(usagePersonalRecords);
        }

        PhysicalExplotarion usagePhysicalExploration = medicalRecordUsage.getPhysicalExplotarion();
        PhysicalExplotarion updatePhysicalExploration = medicalRecord.getPhysicalExplotarion();

        if (updatePhysicalExploration != null) {
            if (updatePhysicalExploration.getWeight() != null) {
                usagePhysicalExploration.setWeight(updatePhysicalExploration.getWeight());
            }
            if (updatePhysicalExploration.getHeight() != null) {
                usagePhysicalExploration.setHeight(updatePhysicalExploration.getHeight());
            }
            if (updatePhysicalExploration.getWeeklyCardiofrequency() != null) {
                usagePhysicalExploration.setWeeklyCardiofrequency(updatePhysicalExploration.getWeeklyCardiofrequency());
            }
            if (!updatePhysicalExploration.getBloodType().isEmpty()) {
                usagePhysicalExploration.setBloodType(updatePhysicalExploration.getBloodType());
            }
            medicalRecordUsage.setPhysicalExplotarion(usagePhysicalExploration);
        }

        FamiliarRecords usageFamiliarRecords = medicalRecordUsage.getFamiliarRecords();
        FamiliarRecords updateFamiliarRecords = medicalRecord.getFamiliarRecords();

        if (updateFamiliarRecords != null) {
            if (!updateFamiliarRecords.getHereditaryDiseases().isEmpty()) {
                usageFamiliarRecords.setHereditaryDiseases(updateFamiliarRecords.getHereditaryDiseases());
            }
            if (!updateFamiliarRecords.getGeneticalPredispositions().isEmpty()) {
                usageFamiliarRecords.setGeneticalPredispositions(updateFamiliarRecords.getGeneticalPredispositions());
            }
            medicalRecordUsage.setFamiliarRecords(usageFamiliarRecords);
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
