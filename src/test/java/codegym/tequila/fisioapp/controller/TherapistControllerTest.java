package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.TherapistDto;
import codegym.tequila.fisioapp.service.TherapistService;
import com.google.gson.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(TherapistController.class)
class TherapistControllerTest {

    private static final String BASE_URL = "/api/therapist";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TherapistService therapistService;

    private Gson gson;
    private TherapistDto therapistDto;
    private TherapistDto therapistDtoWithId;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                        context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
                .create();

        therapistDto = createTherapistDto(null);
        therapistDtoWithId = createTherapistDto(UUID.randomUUID().toString());
    }

    private MockHttpServletResponse performPost(Object content) throws Exception {
        return mockMvc.perform(
                        MockMvcRequestBuilders.post(TherapistControllerTest.BASE_URL)
                                .content(gson.toJson(content))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    @Test
    void createTherapistTest() throws Exception {
        when(therapistService.createTherapist(therapistDto)).thenReturn(therapistDtoWithId);
        //when
        MockHttpServletResponse response = performPost(therapistDto);

        TherapistDto receivedTherapist = gson.fromJson(response.getContentAsString(), TherapistDto.class);
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(receivedTherapist).isEqualTo(therapistDtoWithId);

        verify(therapistService).createTherapist(therapistDto);
        verifyNoMoreInteractions(therapistService);
    }

    @Test
    public void getTherapistTest()throws Exception{
        //Given
        Pageable pageable = PageRequest.of(0, 10);
        List<TherapistDto> therapists = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        TherapistDto therapistDto1 = new TherapistDto();
        TherapistDto therapistDto2 = new TherapistDto();

        therapistDto1.setId(UUID.randomUUID().toString());
        therapistDto1.setFirstname("Firstname");
        therapistDto1.setLastName("lastName");
        therapistDto1.setBirthDate(LocalDate.of(1990,1,1));
        therapistDto1.setGender("gender");
        therapistDto1.setPhone("phone");
        therapistDto1.setAddress("address");
        therapistDto1.setSpecialties("specialties");
        therapists.add(therapistDto1);

        therapistDto2.setId(UUID.randomUUID().toString());
        therapistDto2.setFirstname("Firstname");
        therapistDto2.setLastName("lastName");
        therapistDto2.setBirthDate(LocalDate.of(1990,1,1));
        therapistDto2.setGender("gender");
        therapistDto2.setPhone("phone");
        therapistDto2.setAddress("address");
        therapistDto2.setSpecialties("specialties");
        therapists.add(therapistDto2);

        Page<TherapistDto> therapistsPage = new PageImpl<>(therapists);
        when(therapistService.getTherapists(pageable)).thenReturn(therapistsPage);

        //When
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL))
                .andReturn().getResponse();

        Map map = gson.fromJson(response.getContentAsString(), Map.class);

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        Map therapistDtoReceived1 = (Map) ((ArrayList) map.get("content")).get(0);
        assertThat(therapistDtoReceived1.get("id")).isEqualTo(therapistDto1.getId());
        assertThat(therapistDtoReceived1.get("firstname")).isEqualTo(therapistDto1.getFirstname());
        assertThat(therapistDtoReceived1.get("lastName")).isEqualTo(therapistDto1.getLastName());
        assertThat(therapistDtoReceived1.get("birthDate")).isEqualTo(therapistDto1.getBirthDate().toString());
        assertThat(therapistDtoReceived1.get("gender")).isEqualTo(therapistDto1.getGender());
        assertThat(therapistDtoReceived1.get("phone")).isEqualTo(therapistDto1.getPhone());
        assertThat(therapistDtoReceived1.get("address")).isEqualTo(therapistDto1.getAddress());
        assertThat(therapistDtoReceived1.get("specialties")).isEqualTo(therapistDto1.getSpecialties());

        Map therapistDtoReceived2 = (Map) ((ArrayList) map.get("content")).get(1);
        assertThat(therapistDtoReceived2.get("id")).isEqualTo(therapistDto2.getId());
        assertThat(therapistDtoReceived2.get("firstname")).isEqualTo(therapistDto2.getFirstname());
        assertThat(therapistDtoReceived2.get("lastName")).isEqualTo(therapistDto2.getLastName());
        assertThat(therapistDtoReceived2.get("birthDate")).isEqualTo(therapistDto2.getBirthDate().toString());
        assertThat(therapistDtoReceived2.get("gender")).isEqualTo(therapistDto2.getGender());
        assertThat(therapistDtoReceived2.get("phone")).isEqualTo(therapistDto2.getPhone());
        assertThat(therapistDtoReceived2.get("address")).isEqualTo(therapistDto2.getAddress());
        assertThat(therapistDtoReceived2.get("specialties")).isEqualTo(therapistDto2.getSpecialties());


        verify(therapistService).getTherapists(pageable);
        verifyNoMoreInteractions(therapistService);
    }

    @Test
    public void updateTherapistTest()throws Exception{
        //Given
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        TherapistDto therapistDto = new TherapistDto();
        String id = UUID.randomUUID().toString();

        therapistDto.setFirstname("firstname");
        therapistDto.setLastName("lastName");
        therapistDto.setBirthDate(LocalDate.of(1990,1,1));
        therapistDto.setGender("gender");
        therapistDto.setPhone("phone");
        therapistDto.setAddress("address");
        therapistDto.setSpecialties("specialties");

        TherapistDto therapistDtoWithId = new TherapistDto();
        BeanUtils.copyProperties(therapistDto, therapistDtoWithId);
        therapistDtoWithId.setId(id);

        when(therapistService.updateTherapist(therapistDtoWithId)).thenReturn(therapistDtoWithId);

        //When
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put(BASE_URL + "/" + id)
                        .content(gson.toJson(therapistDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        TherapistDto receivedTherapist = gson.fromJson(response.getContentAsString(), TherapistDto.class);

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(receivedTherapist).isEqualTo(therapistDtoWithId);

        verify(therapistService).updateTherapist(therapistDtoWithId);
        verifyNoMoreInteractions(therapistService);
    }

    @Test
    public void getTherapistByIdTest()throws Exception{

        //Given
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String id = UUID.randomUUID().toString();
        TherapistDto therapistDto = new TherapistDto();

        therapistDto.setId("id");
        therapistDto.setFirstname("firstname");
        therapistDto.setLastName("lastName");
        therapistDto.setBirthDate(LocalDate.of(1990,1,1));
        therapistDto.setGender("gender");
        therapistDto.setPhone("phone");
        therapistDto.setAddress("address");
        therapistDto.setSpecialties("specialties");

        when(therapistService.getTherapistById(id)).thenReturn(therapistDto);

        //When
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        TherapistDto receivedTherapist = gson.fromJson(response.getContentAsString(), TherapistDto.class);

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(receivedTherapist).isEqualTo(therapistDto);

        verify(therapistService).getTherapistById(id);
        verifyNoMoreInteractions(therapistService);

    }

    @Test
    public void deleteTherapistTest()throws Exception{
        //Given
        String id = UUID.randomUUID().toString();

        //When
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete(BASE_URL + "/" + id))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());

        verify(therapistService).deleteTherapist(id);
        verifyNoMoreInteractions(therapistService);

    }

    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate localDate, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(localDate.toString());
        }

        @Override
        public LocalDate deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(jsonElement.getAsString());
        }
    }


    private TherapistDto createTherapistDto(String id) {
        TherapistDto therapistDto = new TherapistDto();
        therapistDto.setId(id);
        therapistDto.setFirstname("firstname");
        therapistDto.setLastName("lastname");
        therapistDto.setBirthDate(LocalDate.of(1990, 1, 1));
        therapistDto.setGender("gender");
        therapistDto.setPhone("phone");
        therapistDto.setAddress("address");
        therapistDto.setSpecialties("specialties");
        return therapistDto;
    }

}