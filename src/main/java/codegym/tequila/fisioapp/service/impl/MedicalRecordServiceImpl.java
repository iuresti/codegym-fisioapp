package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.*;
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
        medicalRecord.setPatient(convertPatientDtoToPatient(medicalRecordDto.getPatient()));
        medicalRecord.setPhysicalExploration(convertPhysicalExplorationDtoToPhysicalExploration(medicalRecordDto.getPhysicalExploration()));
        medicalRecord.setPersonalRecords(convertPersonalRecordsDtoToPersonalRecords(medicalRecordDto.getPersonalRecords()));
        medicalRecord.setFamiliarRecords(convertFamiliarRecordsDtoToFamiliarRecords(medicalRecordDto.getFamiliarRecords()));

        medicalRecordRepository.save(medicalRecord);

        medicalRecordDto.setId(medicalRecord.getId());

        return medicalRecordDto;
    }

    @Override
    public MedicalRecordDto updateMedicalRecord(MedicalRecordDto medicalRecordDto) {
        MedicalRecord medicalRecordUsage = medicalRecordRepository.findById(medicalRecordDto.getId()).orElseThrow(() -> new NoSuchElementException("Medical Record " + medicalRecordDto.getId() + " not found"));

        PersonalRecords usagePersonalRecords = medicalRecordUsage.getPersonalRecords();
        PersonalRecords updatePersonalRecords = convertPersonalRecordsDtoToPersonalRecords(medicalRecordDto.getPersonalRecords());

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
        PhysicalExploration updatePhysicalExploration = convertPhysicalExplorationDtoToPhysicalExploration(medicalRecordDto.getPhysicalExploration());

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
        FamiliarRecords updateFamiliarRecords = convertFamiliarRecordsDtoToFamiliarRecords(medicalRecordDto.getFamiliarRecords());

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

    static MedicalRecordDto convertMedicalRecordToDto(MedicalRecord medicalRecord) {
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        medicalRecordDto.setId(medicalRecord.getId());
        medicalRecordDto.setPatient(convertPatientToDto(medicalRecord.getPatient()));
        medicalRecordDto.setPhysicalExploration(convertPhysicalExplorationToDto(medicalRecord.getPhysicalExploration()));
        medicalRecordDto.setPersonalRecords(convertPersonalRecordsToDto(medicalRecord.getPersonalRecords()));
        medicalRecordDto.setFamiliarRecords(convertFamiliarRecordsToDto(medicalRecord.getFamiliarRecords()));
        return medicalRecordDto;
    }

    private static FamiliarRecordsDto convertFamiliarRecordsToDto(FamiliarRecords familiarRecords) {
        FamiliarRecordsDto familiarRecordsDto = new FamiliarRecordsDto();
        BeanUtils.copyProperties(familiarRecords, familiarRecordsDto);
        return familiarRecordsDto;
    }

    private static PersonalRecordsDto convertPersonalRecordsToDto(PersonalRecords personalRecords) {
        PersonalRecordsDto personalRecordsDto = new PersonalRecordsDto();
        BeanUtils.copyProperties(personalRecords, personalRecordsDto);
        return personalRecordsDto;
    }

    private static PhysicalExplorationDto convertPhysicalExplorationToDto(PhysicalExploration physicalExploration) {
        PhysicalExplorationDto physicalExplorationDto = new PhysicalExplorationDto();
        BeanUtils.copyProperties(physicalExploration, physicalExplorationDto);
        return physicalExplorationDto;
    }

    private static PatientDto convertPatientToDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        BeanUtils.copyProperties(patient, patientDto);
        return patientDto;
    }

    static Patient convertPatientDtoToPatient(PatientDto patientDto) {
        if (patientDto == null) {
            return null;
        }
            Patient patient = new Patient();
            BeanUtils.copyProperties(patientDto, patient);
            return patient;
    }

    static FamiliarRecords convertFamiliarRecordsDtoToFamiliarRecords(FamiliarRecordsDto familiarRecordsDto) {
        if (familiarRecordsDto == null) {
            return null;
        }
            FamiliarRecords familiarRecords = new FamiliarRecords();
            BeanUtils.copyProperties(familiarRecordsDto, familiarRecords);
            return familiarRecords;
    }

    static PersonalRecords convertPersonalRecordsDtoToPersonalRecords(PersonalRecordsDto personalRecordsDto) {
        if (personalRecordsDto == null) {
            return null;
        }
            PersonalRecords personalRecords = new PersonalRecords();
            BeanUtils.copyProperties(personalRecordsDto, personalRecords);
            return personalRecords;
    }

    static PhysicalExploration convertPhysicalExplorationDtoToPhysicalExploration(PhysicalExplorationDto physicalExplorationDto) {
        if (physicalExplorationDto == null) {
            return null;
        }
            PhysicalExploration physicalExploration = new PhysicalExploration();
            BeanUtils.copyProperties(physicalExplorationDto, physicalExploration);
            return physicalExploration;
    }
}
