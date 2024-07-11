package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.*;
import codegym.tequila.fisioapp.model.*;
import codegym.tequila.fisioapp.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.BeanUtils;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MedicalRecordServiceImplTest {
    private static MedicalRecordRepository medicalRecordRepository;
    private static MedicalRecordServiceImpl medicalRecordService;
    private static MedicalRecordDto medicalRecordDto;

    @BeforeAll
    public static void beforeAllSetup() {
        medicalRecordRepository = mock(MedicalRecordRepository.class);
        medicalRecordService = new MedicalRecordServiceImpl(medicalRecordRepository);
        medicalRecordDto = new MedicalRecordDto();
        PatientDto patientDto = new PatientDto();
        PersonalRecordsDto personalRecordsDto = new PersonalRecordsDto();
        PhysicalExplorationDto physicalExplorationDto = new PhysicalExplorationDto();
        FamiliarRecordsDto familiarRecordsDto = new FamiliarRecordsDto();

        patientDto.setId(UUID.randomUUID().toString());
        patientDto.setName("patient");

        personalRecordsDto.setChronicDiseases("No");
        personalRecordsDto.setPreviousSurgeriesAndHospitalizations("No");
        personalRecordsDto.setMedicines("No");
        personalRecordsDto.setAllergies("No");
        personalRecordsDto.setNeurologicalConditions("No");
        personalRecordsDto.setCardiovascularConditions("No");
        personalRecordsDto.setRespiratoryConditions("No");
        personalRecordsDto.setMusculoskeletalConditions("No");

        physicalExplorationDto.setWeight(1.0d);
        physicalExplorationDto.setHeight(1.0d);
        physicalExplorationDto.setBloodType("O+");
        physicalExplorationDto.setWeeklyCardioFrequency(1);

        familiarRecordsDto.setHereditaryDiseases("No");
        familiarRecordsDto.setGeneticalPredispositions("No");

        medicalRecordDto.setPatient(patientDto);
        medicalRecordDto.setPersonalRecords(personalRecordsDto);
        medicalRecordDto.setPhysicalExploration(physicalExplorationDto);
        medicalRecordDto.setFamiliarRecords(familiarRecordsDto);
    }

    @BeforeEach
    public void beforeEachSetup() {
        medicalRecordDto.setId(null);
    }

    @Test
    public void createMedicalRecordTest() {
        //Given:
        ArgumentCaptor<MedicalRecord> medicalRecordArgumentCaptor = ArgumentCaptor.forClass(MedicalRecord.class);
        MedicalRecord medicalRecordDao = new MedicalRecord();

        BeanUtils.copyProperties(medicalRecordDto, medicalRecordDao);
        medicalRecordDao.setId(UUID.randomUUID().toString());

        when(medicalRecordRepository.save(medicalRecordArgumentCaptor.capture())).thenReturn(medicalRecordDao);

        //When:
        MedicalRecordDto medicalRecordDtoReturned = medicalRecordService.createMedicalRecord(medicalRecordDto);

        //Then:
        MedicalRecord medicalRecordSaved = medicalRecordArgumentCaptor.getValue();
        assertNotNull(medicalRecordSaved.getId());
        assertNotNull(medicalRecordSaved.getPatient().getId());
        assertThat(medicalRecordSaved.getPatient().getName()).isEqualTo(medicalRecordDto.getPatient().getName());
        assertThat(medicalRecordSaved.getPhysicalExploration().getHeight()).isEqualTo(medicalRecordDto.getPhysicalExploration().getHeight());
        assertThat(medicalRecordSaved.getPhysicalExploration().getWeight()).isEqualTo(medicalRecordDto.getPhysicalExploration().getWeight());
        assertThat(medicalRecordSaved.getPhysicalExploration().getWeeklyCardioFrequency()).isEqualTo(medicalRecordDto.getPhysicalExploration().getWeeklyCardioFrequency());
        assertThat(medicalRecordSaved.getPhysicalExploration().getBloodType()).isEqualTo(medicalRecordDto.getPhysicalExploration().getBloodType());
        assertThat(medicalRecordSaved.getPersonalRecords().getChronicDiseases()).isEqualTo(medicalRecordDto.getPersonalRecords().getChronicDiseases());
        assertThat(medicalRecordSaved.getPersonalRecords().getPreviousSurgeriesAndHospitalizations()).isEqualTo(medicalRecordDto.getPersonalRecords().getPreviousSurgeriesAndHospitalizations());
        assertThat(medicalRecordSaved.getPersonalRecords().getMedicines()).isEqualTo(medicalRecordDto.getPersonalRecords().getMedicines());
        assertThat(medicalRecordSaved.getPersonalRecords().getAllergies()).isEqualTo(medicalRecordDto.getPersonalRecords().getAllergies());
        assertThat(medicalRecordSaved.getPersonalRecords().getNeurologicalConditions()).isEqualTo(medicalRecordDto.getPersonalRecords().getNeurologicalConditions());
        assertThat(medicalRecordSaved.getPersonalRecords().getCardiovascularConditions()).isEqualTo(medicalRecordDto.getPersonalRecords().getCardiovascularConditions());
        assertThat(medicalRecordSaved.getPersonalRecords().getRespiratoryConditions()).isEqualTo(medicalRecordDto.getPersonalRecords().getRespiratoryConditions());
        assertThat(medicalRecordSaved.getPersonalRecords().getMusculoskeletalConditions()).isEqualTo(medicalRecordDto.getPersonalRecords().getMusculoskeletalConditions());
        assertThat(medicalRecordSaved.getFamiliarRecords().getGeneticalPredispositions()).isEqualTo(medicalRecordDto.getFamiliarRecords().getGeneticalPredispositions());
        assertThat(medicalRecordSaved.getFamiliarRecords().getHereditaryDiseases()).isEqualTo(medicalRecordDto.getFamiliarRecords().getHereditaryDiseases());

        medicalRecordDto.setId(medicalRecordDtoReturned.getId());
        assertThat(medicalRecordDto).isEqualTo(medicalRecordDtoReturned);

        verify(medicalRecordRepository).save(medicalRecordSaved);
        verifyNoMoreInteractions(medicalRecordRepository);
    }

    @Test
    public void updateMedicalRecordTest() {
        //Given:
        ArgumentCaptor<MedicalRecord> medicalRecordArgumentCaptor = ArgumentCaptor.forClass(MedicalRecord.class);
        MedicalRecord medicalRecordDao = new MedicalRecord();

        medicalRecordDto.setId(UUID.randomUUID().toString());

        Patient patientDao = new Patient();
        PersonalRecords personalRecordsDao = new PersonalRecords();
        PhysicalExploration physicalExplorationDao = new PhysicalExploration();
        FamiliarRecords familiarRecordsDao = new FamiliarRecords();

        medicalRecordDao.setId(medicalRecordDto.getId());


        patientDao.setId(medicalRecordDto.getPatient().getId());
        patientDao.setName(medicalRecordDto.getPatient().getName());

        personalRecordsDao.setChronicDiseases("Yes");
        personalRecordsDao.setPreviousSurgeriesAndHospitalizations("Yes");
        personalRecordsDao.setMedicines("Yes");
        personalRecordsDao.setAllergies("Yes");
        personalRecordsDao.setNeurologicalConditions("Yes");
        personalRecordsDao.setCardiovascularConditions("Yes");
        personalRecordsDao.setRespiratoryConditions("Yes");
        personalRecordsDao.setMusculoskeletalConditions("Yes");

        physicalExplorationDao.setWeight(0.0d);
        physicalExplorationDao.setHeight(0.0d);
        physicalExplorationDao.setBloodType("A-");
        physicalExplorationDao.setWeeklyCardioFrequency(8);

        familiarRecordsDao.setHereditaryDiseases("Yes");
        familiarRecordsDao.setGeneticalPredispositions("Yes");

        medicalRecordDao.setPatient(patientDao);
        medicalRecordDao.setPersonalRecords(personalRecordsDao);
        medicalRecordDao.setPhysicalExploration(physicalExplorationDao);
        medicalRecordDao.setFamiliarRecords(familiarRecordsDao);

        when(medicalRecordRepository.findById(medicalRecordDto.getId())).thenReturn(Optional.of(medicalRecordDao));
        when(medicalRecordRepository.save(medicalRecordArgumentCaptor.capture())).thenReturn(medicalRecordDao);

        //When:
        MedicalRecordDto medicalRecordDtoReturned = medicalRecordService.updateMedicalRecord(medicalRecordDto);

        //Then:
        MedicalRecord medicalRecordSaved = medicalRecordArgumentCaptor.getValue();
        assertNotNull(medicalRecordSaved.getId());
        assertNotNull(medicalRecordSaved.getPatient().getId());
        assertThat(medicalRecordSaved.getPatient().getName()).isEqualTo(medicalRecordDto.getPatient().getName());
        assertThat(medicalRecordSaved.getPhysicalExploration().getHeight()).isEqualTo(medicalRecordDto.getPhysicalExploration().getHeight());
        assertThat(medicalRecordSaved.getPhysicalExploration().getWeight()).isEqualTo(medicalRecordDto.getPhysicalExploration().getWeight());
        assertThat(medicalRecordSaved.getPhysicalExploration().getWeeklyCardioFrequency()).isEqualTo(medicalRecordDto.getPhysicalExploration().getWeeklyCardioFrequency());
        assertThat(medicalRecordSaved.getPhysicalExploration().getBloodType()).isEqualTo(medicalRecordDto.getPhysicalExploration().getBloodType());
        assertThat(medicalRecordSaved.getPersonalRecords().getChronicDiseases()).isEqualTo(medicalRecordDto.getPersonalRecords().getChronicDiseases());
        assertThat(medicalRecordSaved.getPersonalRecords().getPreviousSurgeriesAndHospitalizations()).isEqualTo(medicalRecordDto.getPersonalRecords().getPreviousSurgeriesAndHospitalizations());
        assertThat(medicalRecordSaved.getPersonalRecords().getMedicines()).isEqualTo(medicalRecordDto.getPersonalRecords().getMedicines());
        assertThat(medicalRecordSaved.getPersonalRecords().getAllergies()).isEqualTo(medicalRecordDto.getPersonalRecords().getAllergies());
        assertThat(medicalRecordSaved.getPersonalRecords().getNeurologicalConditions()).isEqualTo(medicalRecordDto.getPersonalRecords().getNeurologicalConditions());
        assertThat(medicalRecordSaved.getPersonalRecords().getCardiovascularConditions()).isEqualTo(medicalRecordDto.getPersonalRecords().getCardiovascularConditions());
        assertThat(medicalRecordSaved.getPersonalRecords().getRespiratoryConditions()).isEqualTo(medicalRecordDto.getPersonalRecords().getRespiratoryConditions());
        assertThat(medicalRecordSaved.getPersonalRecords().getMusculoskeletalConditions()).isEqualTo(medicalRecordDto.getPersonalRecords().getMusculoskeletalConditions());
        assertThat(medicalRecordSaved.getFamiliarRecords().getGeneticalPredispositions()).isEqualTo(medicalRecordDto.getFamiliarRecords().getGeneticalPredispositions());
        assertThat(medicalRecordSaved.getFamiliarRecords().getHereditaryDiseases()).isEqualTo(medicalRecordDto.getFamiliarRecords().getHereditaryDiseases());

        assertThat(medicalRecordSaved.getPatient()).withFailMessage("Patient can not be changed").isEqualTo(medicalRecordDao.getPatient());

        medicalRecordDto.setId(medicalRecordDtoReturned.getId());
        assertThat(medicalRecordDto).isEqualTo(medicalRecordDtoReturned);

        verify(medicalRecordRepository).save(medicalRecordSaved);
        verify(medicalRecordRepository).findById(medicalRecordDto.getId());
        verifyNoMoreInteractions(medicalRecordRepository);
    }

    @Test
    public void updateMedicalRecordWithInvalidIdTest() {
        //Given:
        medicalRecordDto.setId(UUID.randomUUID().toString());

        when(medicalRecordRepository.findById(medicalRecordDto.getId())).thenReturn(Optional.empty());

        //When:
        assertThatThrownBy(() -> medicalRecordService.updateMedicalRecord(medicalRecordDto))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Medical Record " + medicalRecordDto.getId() + " not found");
        //Then:
        verify(medicalRecordRepository).findById(medicalRecordDto.getId());
        verifyNoMoreInteractions(medicalRecordRepository);

    }

    @Test
    public void findByIdTest() {
        //Given:
        MedicalRecord medicalRecordDao = new MedicalRecord();
        String id = UUID.randomUUID().toString();

        medicalRecordDao.setId(id);
        medicalRecordDao.setPhysicalExploration(MedicalRecordServiceImpl.convertPhysicalExplorationDtoToPhysicalExploration(medicalRecordDto.getPhysicalExploration()));
        medicalRecordDao.setPatient(MedicalRecordServiceImpl.convertPatientDtoToPatient(medicalRecordDto.getPatient()));
        medicalRecordDao.setPersonalRecords(MedicalRecordServiceImpl.convertPersonalRecordsDtoToPersonalRecords(medicalRecordDto.getPersonalRecords()));
        medicalRecordDao.setFamiliarRecords(MedicalRecordServiceImpl.convertFamiliarRecordsDtoToFamiliarRecords(medicalRecordDto.getFamiliarRecords()));


        when(medicalRecordRepository.findById(id)).thenReturn(Optional.of(medicalRecordDao));

        //When:
        MedicalRecordDto medicalRecordDtoResult = medicalRecordService.findById(id);

        //Then:
        assertThat(medicalRecordDtoResult).isNotNull();
        assertThat(medicalRecordDtoResult.getId()).isEqualTo(id);
        assertThat(medicalRecordDtoResult).isEqualTo(MedicalRecordServiceImpl.convertMedicalRecordToDto(medicalRecordDao));

        verify(medicalRecordRepository).findById(id);
        verifyNoMoreInteractions(medicalRecordRepository);
    }

}
