package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.service.TherapyService;
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

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(TherapyController.class)
class TherapyControllerTest {

    private static final String BASE_URL = "/api/therapy";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TherapyService therapyService;

    @Test
    public void createTherapy_Test() throws Exception {
        //Given
        Gson gson = new Gson();
        TherapyDto therapyDto = new TherapyDto();
        TherapyDto therapyDtoWithId = new TherapyDto();

        therapyDto.setName("Name Therapy");
        therapyDto.setDescription("Description Therapy");

        BeanUtils.copyProperties(therapyDto, therapyDtoWithId);
        therapyDtoWithId.setId(UUID.randomUUID().toString());

        when(therapyService.createTherapy(therapyDto)).thenReturn(therapyDtoWithId);

        //When
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL)
                        .content(gson.toJson(therapyDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        TherapyDto receivedTherapy = gson.fromJson(response.getContentAsString(), TherapyDto.class);

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(therapyDtoWithId).isEqualTo(receivedTherapy);

        verify(therapyService).createTherapy(therapyDto);
        verifyNoMoreInteractions(therapyService);
    }

    @Test
    public void updateTherapy_Test() throws Exception {
        //Given
        Gson gson = new Gson();

        String id = UUID.randomUUID().toString();

        TherapyDto therapyDto = new TherapyDto();
        therapyDto.setName("Name Therapy");
        therapyDto.setDescription("Description Therapy");

        TherapyDto therapyDtoWithId = new TherapyDto();
        BeanUtils.copyProperties(therapyDto, therapyDtoWithId);
        therapyDtoWithId.setId(id);

        when(therapyService.updateTherapy(id, therapyDto)).thenReturn(therapyDtoWithId);

        //When
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put(BASE_URL + "/" + id)
                        .content(gson.toJson(therapyDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        TherapyDto recivedTherapy = gson.fromJson(response.getContentAsString(), TherapyDto.class);

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(recivedTherapy).isEqualTo(therapyDtoWithId);

        verify(therapyService).updateTherapy(id, therapyDto);
        verifyNoMoreInteractions(therapyService);
    }

    @Test
    public void updateTherapyWithException_Test() throws Exception {
        //Given
        Gson gson = new Gson();

        String id = UUID.randomUUID().toString();

        TherapyDto therapyDto = new TherapyDto();
        therapyDto.setName("Name Therapy");
        therapyDto.setDescription("Description Therapy");

        TherapyDto therapyDtoWithId = new TherapyDto();
        BeanUtils.copyProperties(therapyDto, therapyDtoWithId);
        therapyDtoWithId.setId(id);

        when(therapyService.updateTherapy(id, therapyDto)).thenThrow(new NoSuchElementException("Therapy not found"));

        //When
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.put(BASE_URL + "/" + id)
                                .content(gson.toJson(therapyDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        verify(therapyService).updateTherapy(id, therapyDto);
        verifyNoMoreInteractions(therapyService);
    }

    @Test
    void deactivateTherapy() {
    }

    @Test
    void activateTherapy() {
    }

    @Test
    public void getTherapyById_Test() throws Exception {
        //Given
        Gson gson = new Gson();

        String id = UUID.randomUUID().toString();

        TherapyDto therapyDtoWithId = new TherapyDto();
        therapyDtoWithId.setId(id);
        therapyDtoWithId.setName("X");
        therapyDtoWithId.setDescription("Y");

        when(therapyService.getTherapy(id)).thenReturn(therapyDtoWithId);

        //When
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put(BASE_URL + "/" + id)
                        .content(gson.toJson(therapyDtoWithId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        TherapyDto receivedTherapy = gson.fromJson(response.getContentAsString(), TherapyDto.class);

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        //assertThat(receivedTherapy).isEqualTo(receivedTherapy);

        //verify(therapyService).getTherapy(id);
        //verifyNoMoreInteractions(therapyService);
    }

    @Test
    void getTherapies() {
    }
}