package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.*;
import codegym.tequila.fisioapp.model.*;
import codegym.tequila.fisioapp.repository.MedicalRecordRepository;
import codegym.tequila.fisioapp.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class PatientServiceImplTest {

    @Test
    public void createPatientTest() {
        //Given:
        PatientRepository patientRepository = mock(PatientRepository.class);
        MedicalRecordRepository medicalRecordRepository = mock(MedicalRecordRepository.class);
        PatientServiceImpl patientService = new PatientServiceImpl(patientRepository, medicalRecordRepository);
        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);

        PatientDto patientDto = new PatientDto();
        Patient patientDao = new Patient();

        patientDto.setName("patient");

        patientDao.setId(UUID.randomUUID().toString());
        patientDao.setName(patientDto.getName());

        when(patientRepository.save(patientArgumentCaptor.capture())).thenReturn(patientDao);

        //When:
        PatientDto patientDtoReturned = patientService.createPatient(patientDto);

        //Then:
        Patient patientSaved = patientArgumentCaptor.getValue();
        assertThat(patientSaved.getId()).isNotNull();
        assertThat(patientSaved.getName()).isEqualTo(patientDto.getName());

        patientDto.setId(patientDtoReturned.getId());
        assertThat(patientDto).isEqualTo(patientDtoReturned);

        verify(patientRepository).save(patientSaved);
        verifyNoMoreInteractions(patientRepository);
    }

    @Test
    public void getMedicalRecordForPatientTest() {
        //Given:
        PatientRepository patientRepository = mock(PatientRepository.class);
        MedicalRecordRepository medicalRecordRepository = mock(MedicalRecordRepository.class);
        PatientServiceImpl patientService = new PatientServiceImpl(patientRepository, medicalRecordRepository);
        String id = UUID.randomUUID().toString();

        MedicalRecord medicalRecord = new MedicalRecord();
        Patient patient = new Patient();

        PersonalRecords personalRecords = new PersonalRecords();
        PhysicalExploration physicalExploration = new PhysicalExploration();
        FamiliarRecords familiarRecords = new FamiliarRecords();

        patient.setId(id);
        patient.setName("patient");

        personalRecords.setChronicDiseases("No");
        personalRecords.setPreviousSurgeriesAndHospitalizations("No");
        personalRecords.setMedicines("No");
        personalRecords.setAllergies("No");
        personalRecords.setNeurologicalConditions("No");
        personalRecords.setCardiovascularConditions("No");
        personalRecords.setRespiratoryConditions("No");
        personalRecords.setMusculoskeletalConditions("No");

        physicalExploration.setWeight(1.0d);
        physicalExploration.setHeight(1.0d);
        physicalExploration.setBloodType("O+");
        physicalExploration.setWeeklyCardioFrequency(1);

        familiarRecords.setHereditaryDiseases("No");
        familiarRecords.setGeneticalPredispositions("No");

        medicalRecord.setId(id);
        medicalRecord.setPatient(patient);
        medicalRecord.setPersonalRecords(personalRecords);
        medicalRecord.setPhysicalExploration(physicalExploration);
        medicalRecord.setFamiliarRecords(familiarRecords);

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatientId(id)).thenReturn(Optional.of(medicalRecord));

        //When:
        MedicalRecordDto medicalRecordDtoReceived = patientService.getMedicalRecordForPatient(id);

        //Then:
        assertThat(medicalRecordDtoReceived).isEqualTo(MedicalRecordServiceImpl.convertMedicalRecordToDto(medicalRecord));
        assertThat(medicalRecordDtoReceived.getPatient().getId()).isEqualTo(patient.getId());

        verify(medicalRecordRepository).findByPatientId(id);
        verifyNoMoreInteractions(medicalRecordRepository);
    }

    @Test
    public void getMedicalRecordForPatientWithNoExistentMedicalRecordTest() {
        //Given:
        PatientRepository patientRepository = mock(PatientRepository.class);
        MedicalRecordRepository medicalRecordRepository = mock(MedicalRecordRepository.class);
        PatientServiceImpl patientService = new PatientServiceImpl(patientRepository, medicalRecordRepository);
        String id = UUID.randomUUID().toString();

        Patient patient = new Patient();
        patient.setId(id);
        patient.setName("patient");

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(medicalRecordRepository.findByPatientId(id)).thenReturn(Optional.empty());

        //When:
        assertThatThrownBy(() -> patientService.getMedicalRecordForPatient(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Medical Record for Patient " + patient.getId() + " not found");
        //Then:
        verify(patientRepository).findById(id);
        verify(medicalRecordRepository).findByPatientId(id);
        verifyNoMoreInteractions(medicalRecordRepository);
    }

    @Test
    public void getMedicalRecordForPatientWithNoExistentPatientTest() {
        //Given:
        PatientRepository patientRepository = mock(PatientRepository.class);
        MedicalRecordRepository medicalRecordRepository = mock(MedicalRecordRepository.class);
        PatientServiceImpl patientService = new PatientServiceImpl(patientRepository, medicalRecordRepository);
        String id = UUID.randomUUID().toString();

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        //When:
        assertThatThrownBy(() -> patientService.getMedicalRecordForPatient(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Patient " + id + " not found");
        //Then:
        verify(patientRepository).findById(id);
        verifyNoMoreInteractions(patientRepository);
    }
}
