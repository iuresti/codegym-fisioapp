package codegym.tequila.fisioapp.controller;


import codegym.tequila.fisioapp.dto.*;
import codegym.tequila.fisioapp.service.impl.MedicalRecordServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

    private static final String BASE_URL = "/api/medical-record";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordServiceImpl medicalRecordService;

    private static MedicalRecordDto medicalRecordDto;

    @BeforeAll
    public static void setup(){
        medicalRecordDto = new MedicalRecordDto();
        PatientDto patientDto = new PatientDto();
        PersonalRecordsDto personalRecordsDto = new PersonalRecordsDto();
        PhysicalExplorationDto physicalExplorationDto = new PhysicalExplorationDto();
        FamiliarRecordsDto familiarRecords = new FamiliarRecordsDto();

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

        familiarRecords.setHereditaryDiseases("No");
        familiarRecords.setGeneticalPredispositions("No");

        medicalRecordDto.setPatient(patientDto);
        medicalRecordDto.setPersonalRecords(personalRecordsDto);
        medicalRecordDto.setPhysicalExploration(physicalExplorationDto);
        medicalRecordDto.setFamiliarRecords(familiarRecords);
    }

    @Test
    public void createMedicalRecordTest() throws Exception {
        // Given:
        Gson gson = new Gson();
        MedicalRecordDto medicalRecordDtoWithId = new MedicalRecordDto();
        String id = UUID.randomUUID().toString();

        BeanUtils.copyProperties(medicalRecordDto, medicalRecordDtoWithId);
        medicalRecordDtoWithId.setId(id);

        when(medicalRecordService.createMedicalRecord(medicalRecordDto)).thenReturn(medicalRecordDtoWithId);

        // When:
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_URL)
                                .content(gson.toJson(medicalRecordDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        MedicalRecordDto receivedMedicalRecord = gson.fromJson(response.getContentAsString(), MedicalRecordDto.class);

        // Then:
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(medicalRecordDtoWithId).isEqualTo(receivedMedicalRecord);

        verify(medicalRecordService).createMedicalRecord(medicalRecordDto);
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    public void updateMedicalRecordTest() throws Exception {
        // Given:
        Gson gson = new Gson();
        MedicalRecordDto medicalRecordDtoWithId = new MedicalRecordDto();
        String id = UUID.randomUUID().toString();

        BeanUtils.copyProperties(medicalRecordDto, medicalRecordDtoWithId);
        medicalRecordDtoWithId.setId(id);

        when(medicalRecordService.updateMedicalRecord(medicalRecordDtoWithId)).thenReturn(medicalRecordDtoWithId);

        // When:
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.put(BASE_URL + "/" + id)
                                .content(gson.toJson(medicalRecordDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        MedicalRecordDto receivedMedicalRecord = gson.fromJson(response.getContentAsString(), MedicalRecordDto.class);

        // Then:
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(medicalRecordDtoWithId).isEqualTo(receivedMedicalRecord);

        verify(medicalRecordService).updateMedicalRecord(medicalRecordDtoWithId);
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    public void findByIdTest() throws Exception {
        // Given:
        Gson gson = new Gson();
        MedicalRecordDto medicalRecordDtoWithId = new MedicalRecordDto();
        String id = UUID.randomUUID().toString();

        BeanUtils.copyProperties(medicalRecordDto, medicalRecordDtoWithId);
        medicalRecordDtoWithId.setId(id);

        when(medicalRecordService.findById(id)).thenReturn(medicalRecordDtoWithId);

        // When:
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.get(BASE_URL + "/" + id)
                                .content(gson.toJson(medicalRecordDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        MedicalRecordDto receivedMedicalRecord = gson.fromJson(response.getContentAsString(), MedicalRecordDto.class);

        // Then:
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(medicalRecordDtoWithId).isEqualTo(receivedMedicalRecord);

        verify(medicalRecordService).findById(id);
        verifyNoMoreInteractions(medicalRecordService);
    }
}
