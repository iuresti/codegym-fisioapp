package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.*;
import codegym.tequila.fisioapp.service.PatientService;
import com.google.gson.Gson;
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

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    private static final String BASE_URL = "/api/patient";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    public void createPatientTest() throws Exception {
        //Given:
        Gson gson = new Gson();
        PatientDto patientDto = new PatientDto();
        PatientDto patientDtoWithId = new PatientDto();

        patientDto.setName("patient");

        BeanUtils.copyProperties(patientDto, patientDtoWithId);
        patientDtoWithId.setId(UUID.randomUUID().toString());

        when(patientService.createPatient(patientDto)).thenReturn(patientDtoWithId);

        //When:
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL)
                        .content(gson.toJson(patientDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        PatientDto receivedPatient = gson.fromJson(response.getContentAsString(), PatientDto.class);

        //Then:
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(patientDtoWithId).isEqualTo(receivedPatient);

        verify(patientService).createPatient(patientDto);
        verifyNoMoreInteractions(patientService);
    }

    @Test
    public void getPatientMedicalRecordTest() throws Exception {
        //Given:
        Gson gson = new Gson();
        String id = UUID.randomUUID().toString();
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        PatientDto patientDto = new PatientDto();

        PersonalRecordsDto personalRecordsDto = new PersonalRecordsDto();
        PhysicalExplorationDto physicalExplorationDto = new PhysicalExplorationDto();
        FamiliarRecordsDto familiarRecords = new FamiliarRecordsDto();

        patientDto.setId(id);
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

        medicalRecordDto.setId(id);
        medicalRecordDto.setPatient(patientDto);
        medicalRecordDto.setPersonalRecords(personalRecordsDto);
        medicalRecordDto.setPhysicalExploration(physicalExplorationDto);
        medicalRecordDto.setFamiliarRecords(familiarRecords);

        when(patientService.getMedicalRecordForPatient(id)).thenReturn(medicalRecordDto);

        //When:
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.get(BASE_URL+"/"+id+"/medical-record")
                                .content(gson.toJson(patientDto.getId()))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        MedicalRecordDto medicalRecordDtoResponse = gson.fromJson(response.getContentAsString(), MedicalRecordDto.class);

        //Then:
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(medicalRecordDto).isEqualTo(medicalRecordDtoResponse);

        verify(patientService).getMedicalRecordForPatient(id);
        verifyNoMoreInteractions(patientService);
    }
}
