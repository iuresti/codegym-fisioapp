package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.model.*;
import codegym.tequila.fisioapp.repository.MedicalRecordRepository;
import codegym.tequila.fisioapp.service.MedicalRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecord.setId(UUID.randomUUID().toString());
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordUsage = medicalRecordRepository.findById(medicalRecord.getId()).orElseThrow();

        PersonalRecords usagePersonalRecords = medicalRecordUsage.getPersonalRecords();
        PersonalRecords updatePersonalRecords = medicalRecord.getPersonalRecords();

        if (updatePersonalRecords != null) {
            if (StringUtils.hasText(updatePersonalRecords.getChronicDiseases())) {
                usagePersonalRecords.setChronicDiseases(updatePersonalRecords.getChronicDiseases());
            }
            if (StringUtils.hasText(updatePersonalRecords.getPreviousSurgeriesAndHospitalizations())) {
                usagePersonalRecords.setPreviousSurgeriesAndHospitalizations(updatePersonalRecords.getPreviousSurgeriesAndHospitalizations());
            }
            if (StringUtils.hasText(updatePersonalRecords.getMedicines())) {
                usagePersonalRecords.setMedicines(updatePersonalRecords.getMedicines());
            }
            if (StringUtils.hasText(updatePersonalRecords.getAllergies())) {
                usagePersonalRecords.setAllergies(updatePersonalRecords.getAllergies());
            }
            if (StringUtils.hasText(updatePersonalRecords.getNeurologicalConditions())) {
                usagePersonalRecords.setNeurologicalConditions(updatePersonalRecords.getNeurologicalConditions());
            }
            if (StringUtils.hasText(updatePersonalRecords.getCardiovascularConditions())) {
                usagePersonalRecords.setCardiovascularConditions(updatePersonalRecords.getCardiovascularConditions());
            }
            if (StringUtils.hasText(updatePersonalRecords.getRespiratoryConditions())) {
                usagePersonalRecords.setRespiratoryConditions(updatePersonalRecords.getRespiratoryConditions());
            }
            if (StringUtils.hasText(updatePersonalRecords.getMusculoskeletalConditions())) {
                usagePersonalRecords.setMusculoskeletalConditions(updatePersonalRecords.getMusculoskeletalConditions());
            }
            medicalRecordUsage.setPersonalRecords(usagePersonalRecords);
        }

        PhysicalExploration usagePhysicalExploration = medicalRecordUsage.getPhysicalExploration();
        PhysicalExploration updatePhysicalExploration = medicalRecord.getPhysicalExploration();

        if (updatePhysicalExploration != null) {
            if (updatePhysicalExploration.getWeight() != null) {
                usagePhysicalExploration.setWeight(updatePhysicalExploration.getWeight());
            }
            if (updatePhysicalExploration.getHeight() != null) {
                usagePhysicalExploration.setHeight(updatePhysicalExploration.getHeight());
            }
            if (updatePhysicalExploration.getWeeklyCardioFrequency() != null) {
                usagePhysicalExploration.setWeeklyCardioFrequency(updatePhysicalExploration.getWeeklyCardioFrequency());
            }
            if (StringUtils.hasText(updatePhysicalExploration.getBloodType())) {
                usagePhysicalExploration.setBloodType(updatePhysicalExploration.getBloodType());
            }
            medicalRecordUsage.setPhysicalExploration(usagePhysicalExploration);
        }

        FamiliarRecords usageFamiliarRecords = medicalRecordUsage.getFamiliarRecords();
        FamiliarRecords updateFamiliarRecords = medicalRecord.getFamiliarRecords();

        if (updateFamiliarRecords != null) {
            if (StringUtils.hasText(usageFamiliarRecords.getHereditaryDiseases())) {
                usageFamiliarRecords.setHereditaryDiseases(updateFamiliarRecords.getHereditaryDiseases());
            }
            if (StringUtils.hasText(usageFamiliarRecords.getGeneticalPredispositions())) {
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
