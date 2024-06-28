package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.service.UserService;
import com.google.gson.Gson;
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

import java.util.*;

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
    public void createUserTest() throws Exception {
        // Given:
        Gson gson = new Gson();
        UserDto userDto = new UserDto();
        UserDto userDtoWithId = new UserDto();

        userDto.setUser("user");
        userDto.setPassword("password");
        userDto.setAvatar("url");
        userDto.setName("name");
        userDto.setLastName("lastName");

        BeanUtils.copyProperties(userDto, userDtoWithId);
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

    @Test
    public void getUserTest() throws Exception {
        // Given:
        Pageable pageable = PageRequest.of(0, 10);
        List<UserDto> users = new ArrayList<>();
        Gson gson = new Gson();
        UserDto userDto1 = new UserDto();
        UserDto userDto2 = new UserDto();

        userDto1.setId(UUID.randomUUID().toString());
        userDto1.setUser("user1");
        userDto1.setPassword("password1");
        userDto1.setAvatar("url1");
        userDto1.setName("name1");
        userDto1.setLastName("lastName1");
        userDto1.setEmail("email1");
        users.add(userDto1);

        userDto2.setUser("user2");
        userDto2.setPassword("password2");
        userDto2.setAvatar("url2");
        userDto2.setName("name2");
        userDto2.setLastName("lastName2");
        userDto2.setEmail("email2");
        userDto2.setId(UUID.randomUUID().toString());
        users.add(userDto2);


        Page<UserDto> usersPage = new PageImpl<>(users);
        when(userService.getUsers(pageable)).thenReturn(usersPage);

        // When:
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.get(BASE_URL))
                .andReturn().getResponse();

        Map map = gson.fromJson(response.getContentAsString(), Map.class);


        // Then:
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        Map userDtoReceived1 = (Map) ((ArrayList) map.get("content")).get(0);
        assertThat(userDtoReceived1.get("name")).isEqualTo(userDto1.getName());
        assertThat(userDtoReceived1.get("lastName")).isEqualTo(userDto1.getLastName());
        assertThat(userDtoReceived1.get("user")).isEqualTo(userDto1.getUser());
        assertThat(userDtoReceived1.get("password")).isEqualTo(userDto1.getPassword());
        assertThat(userDtoReceived1.get("email")).isEqualTo(userDto1.getEmail());
        assertThat(userDtoReceived1.get("avatar")).isEqualTo(userDto1.getAvatar());

        Map userDtoReceived2 = (Map) ((ArrayList) map.get("content")).get(1);
        assertThat(userDtoReceived2.get("name")).isEqualTo(userDto2.getName());
        assertThat(userDtoReceived2.get("lastName")).isEqualTo(userDto2.getLastName());
        assertThat(userDtoReceived2.get("user")).isEqualTo(userDto2.getUser());
        assertThat(userDtoReceived2.get("password")).isEqualTo(userDto2.getPassword());
        assertThat(userDtoReceived2.get("email")).isEqualTo(userDto2.getEmail());
        assertThat(userDtoReceived2.get("avatar")).isEqualTo(userDto2.getAvatar());

        verify(userService).getUsers(pageable);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateUserTest() throws Exception {
        // Given:
        Gson gson = new Gson();
        UserDto userDto = new UserDto();
        UserDto userDtoWithId = new UserDto();
        String id = UUID.randomUUID().toString();

        userDto.setUser("user");
        userDto.setPassword("password");
        userDto.setAvatar("url");
        userDto.setName("name");
        userDto.setLastName("lastName");

        BeanUtils.copyProperties(userDto, userDtoWithId);
        userDtoWithId.setId(id);


        when(userService.updateUser(userDtoWithId)).thenReturn(userDtoWithId);

        // When:
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.put(BASE_URL + "/" + id)
                                .content(gson.toJson(userDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        UserDto receivedUser = gson.fromJson(response.getContentAsString(), UserDto.class);

        // Then:
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(receivedUser).isEqualTo(userDtoWithId);

        verify(userService).updateUser(userDtoWithId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateUserWithExceptionTest() throws Exception {
        // Given:
        Gson gson = new Gson();
        UserDto userDto = new UserDto();
        UserDto userDtoWithId = new UserDto();
        String id = UUID.randomUUID().toString();

        userDto.setUser("user");
        userDto.setPassword("password");
        userDto.setAvatar("url");
        userDto.setName("name");
        userDto.setLastName("lastName");

        BeanUtils.copyProperties(userDto, userDtoWithId);
        userDtoWithId.setId(id);


        when(userService.updateUser(userDtoWithId)).thenThrow(new NoSuchElementException("User not found"));

        // When:
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.put(BASE_URL + "/" + id)
                                .content(gson.toJson(userDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Then:
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        verify(userService).updateUser(userDtoWithId);
        verifyNoMoreInteractions(userService);
    }
}
