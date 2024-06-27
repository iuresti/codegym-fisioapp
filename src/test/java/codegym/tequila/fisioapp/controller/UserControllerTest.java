package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String BASE_URL = "/api/user";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;



    @Test
    public void createUser() throws Exception {
        // Given:
        Gson gson = new Gson();
        UserDto userDto = new UserDto();
        UserDto userDtoWithId = new UserDto();

        userDto.setUser("user");
        userDto.setPassword("password");
        userDto.setAvatar("url");
        userDto.setName("name");
        userDto.setLastName("lastName");

        userDtoWithId.setUser("user");
        userDtoWithId.setPassword("password");
        userDtoWithId.setAvatar("url");
        userDtoWithId.setName("name");
        userDtoWithId.setLastName("lastName");
        userDtoWithId.setId(UUID.randomUUID().toString());


        when(userService.createUser(userDto)).thenReturn(userDtoWithId);

        // When:
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_URL)
                                .content(gson.toJson(userDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        UserDto receivedUser = gson.fromJson(response.getContentAsString(), UserDto.class);

        // Then:
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(userDtoWithId).isEqualTo(receivedUser);

        verify(userService).createUser(userDto);
        verifyNoMoreInteractions(userService);
    }
}
