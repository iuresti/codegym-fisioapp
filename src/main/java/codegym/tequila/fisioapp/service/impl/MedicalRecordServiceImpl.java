package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.MedicalRecordDto;
import codegym.tequila.fisioapp.model.*;
import codegym.tequila.fisioapp.repository.MedicalRecordRepository;
import codegym.tequila.fisioapp.service.MedicalRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public MedicalRecordDto createMedicalRecord(MedicalRecordDto medicalRecordDto) {
        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setId(UUID.randomUUID().toString());
        medicalRecord.setPatient(medicalRecordDto.getPatient());
        medicalRecord.setPhysicalExploration(medicalRecordDto.getPhysicalExploration());
        medicalRecord.setPersonalRecords(medicalRecordDto.getPersonalRecords());
        medicalRecord.setFamiliarRecords(medicalRecordDto.getFamiliarRecords());

        medicalRecordRepository.save(medicalRecord);

        medicalRecordDto.setId(medicalRecord.getId());

        return medicalRecordDto;
    }

    @Override
    public MedicalRecordDto updateMedicalRecord(MedicalRecordDto medicalRecordDto) {
        MedicalRecord medicalRecordUsage = medicalRecordRepository.findById(medicalRecordDto.getId()).orElseThrow(() -> new NoSuchElementException("Medical Record " + medicalRecordDto.getId() + " not found"));

        PersonalRecords usagePersonalRecords = medicalRecordUsage.getPersonalRecords();
        PersonalRecords updatePersonalRecords = medicalRecordDto.getPersonalRecords();

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
        PhysicalExploration updatePhysicalExploration = medicalRecordDto.getPhysicalExploration();

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
        FamiliarRecords updateFamiliarRecords = medicalRecordDto.getFamiliarRecords();

        if (updateFamiliarRecords != null) {
            if (StringUtils.hasText(usageFamiliarRecords.getHereditaryDiseases())) {
                usageFamiliarRecords.setHereditaryDiseases(updateFamiliarRecords.getHereditaryDiseases());
            }
            if (StringUtils.hasText(usageFamiliarRecords.getGeneticalPredispositions())) {
                usageFamiliarRecords.setGeneticalPredispositions(updateFamiliarRecords.getGeneticalPredispositions());
            }
            medicalRecordUsage.setFamiliarRecords(usageFamiliarRecords);
        }

        return convertMedicalRecordToDto(medicalRecordRepository.save(medicalRecordUsage));
    }

    @Override
    public MedicalRecordDto findById(String id) {
        return convertMedicalRecordToDto(medicalRecordRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Medical Record " + id + " not found")));
    }

    private static MedicalRecordDto convertMedicalRecordToDto(MedicalRecord medicalRecord) {
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();

        BeanUtils.copyProperties(medicalRecord, medicalRecordDto);

        return medicalRecordDto;
    }
}
