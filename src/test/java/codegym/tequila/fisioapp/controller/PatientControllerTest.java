package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.PatientDto;
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
}
