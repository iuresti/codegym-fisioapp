package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.model.User;
import codegym.tequila.fisioapp.repository.UserRepository;
import codegym.tequila.fisioapp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Test
    void createUserTest() {
        // Given:
        UserRepository userRepository = mock(UserRepository.class);
        EmailService emailService = mock(EmailService.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, emailService);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        UserDto userDto = new UserDto();
        User userDao = new User();

        userDto.setName("name");
        userDto.setLastName("lastName");
        userDto.setUser("user");
        userDto.setPassword("password");
        userDto.setAvatar("url");
        userDto.setEmail("theEmail");

        BeanUtils.copyProperties(userDto, userDao);
        userDao.setId(UUID.randomUUID().toString());

        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(userDao);

        // When:
        UserDto userDtoReturned = userService.createUser(userDto);

        // Then:
        User savedUser = userArgumentCaptor.getValue();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo(userDto.getName());
        assertThat(savedUser.getLastName()).isEqualTo(userDto.getLastName());
        assertThat(savedUser.getAvatar()).isEqualTo(userDto.getAvatar());
        assertThat(savedUser.getUser()).isEqualTo(userDto.getUser());
        assertThat(BCrypt.checkpw(userDto.getPassword(), savedUser.getPassword())).isTrue();
        assertThat(savedUser.getEmail()).isEqualTo(userDto.getEmail());

        userDto.setId(userDtoReturned.getId());
        assertThat(userDto).isEqualTo(userDtoReturned);

        verify(userRepository).save(savedUser);
        verify(emailService).sendSimpleEmail(savedUser.getEmail(), "User created", String.format("Tu usuario %s ha sido creado", savedUser.getUser()));
        verifyNoMoreInteractions(userRepository, emailService);
    }

    @Test
    void updateUserTest() {
        // Given:
        UserRepository userRepository = mock(UserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, null);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        UserDto userDto = new UserDto();

        userDto.setId(UUID.randomUUID().toString());
        userDto.setName("name");
        userDto.setLastName("lastName");
        userDto.setUser("user");
        userDto.setPassword("password");
        userDto.setAvatar("url");
        userDto.setEmail("theEmail");

        User userDao = new User();
        userDao.setId(userDto.getId());
        userDao.setName("A");
        userDao.setLastName("B");
        userDao.setUser("C");
        userDao.setPassword("D");
        userDao.setAvatar("E");
        userDao.setEmail("F");

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userDao));
        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(userDao);

        // When:
        UserDto userDtoReturned = userService.updateUser(userDto);

        // Then:
        User savedUser = userArgumentCaptor.getValue();
        assertThat(savedUser.getId()).isEqualTo(userDto.getId());
        assertThat(savedUser.getName()).isEqualTo(userDto.getName());
        assertThat(savedUser.getLastName()).isEqualTo(userDto.getLastName());
        assertThat(savedUser.getAvatar()).isEqualTo(userDto.getAvatar());
        assertThat(savedUser.getUser()).withFailMessage("User must not be modified").isEqualTo(userDao.getUser());
        assertThat(savedUser.getPassword()).withFailMessage("Password must not be modified").isEqualTo(userDao.getPassword());
        assertThat(savedUser.getEmail()).isEqualTo(userDto.getEmail());

        BeanUtils.copyProperties(userDao, userDto);
        userDto.setPassword(null);
        assertThat(userDto).isEqualTo(userDtoReturned);

        verify(userRepository).findById(userDto.getId());
        verify(userRepository).save(savedUser);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUserWithInvalidUserTest() {
        // Given:
        UserRepository userRepository = mock(UserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, null);
        UserDto userDto = new UserDto();

        userDto.setId(UUID.randomUUID().toString());

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.empty());

        // When:
        assertThatThrownBy(() -> userService.updateUser(userDto))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("User " + userDto.getId() + " not found");

        // Then:
        verify(userRepository).findById(userDto.getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUserWithNoUpdatesTest() {
        // Given:
        UserRepository userRepository = mock(UserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, null);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        UserDto userDto = new UserDto();
        User userDao = new User();

        userDto.setId(UUID.randomUUID().toString());

        userDao = createUser(userDto.getId(), "A", "B", "C", "D", "E", "F");

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userDao));
        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(userDao);

        // When:
        UserDto userDtoReturned = userService.updateUser(userDto);

        // Then:
        User savedUser = userArgumentCaptor.getValue();
        assertThat(savedUser.getId()).isEqualTo(userDto.getId());
        assertThat(savedUser.getName()).isEqualTo(userDao.getName());
        assertThat(savedUser.getLastName()).isEqualTo(userDao.getLastName());
        assertThat(savedUser.getAvatar()).isEqualTo(userDao.getAvatar());
        assertThat(savedUser.getUser()).isEqualTo(userDao.getUser());
        assertThat(savedUser.getPassword()).isEqualTo(userDao.getPassword());
        assertThat(savedUser.getEmail()).isEqualTo(userDao.getEmail());

        BeanUtils.copyProperties(userDao, userDto);
        userDto.setPassword(null);
        assertThat(userDto).isEqualTo(userDtoReturned);

        verify(userRepository).findById(userDto.getId());
        verify(userRepository).save(savedUser);
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    void getUsersTest() {
        // Given:
        Pageable pageable = PageRequest.of(0, 10);
        UserRepository userRepository = mock(UserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository, null);
        List<User> users = new ArrayList<>();

        users.add(createUser("1", "A1", "B1", "C1", "D1", "F1", "G1"));
        users.add(createUser("2", "A2", "B2", "C2", "D2", "F2", "G2"));
        users.add(createUser("3", "A3", "B3", "C3", "D3", "F3", "G3"));

        Page<User> usersPage = new PageImpl<>(users);

        when(userRepository.findAll(pageable)).thenReturn(usersPage);

        // When:
        Page<UserDto> usersDtoReturned = userService.getUsers(pageable);

        // Then:
        assertThat(usersDtoReturned.getContent()).containsExactly(
                createUserDto("1", "A1", "B1", "C1", null, "F1", "G1"),
                createUserDto("2", "A2", "B2", "C2", null, "F2", "G2"),
                createUserDto("3", "A3", "B3", "C3", null, "F3", "G3")
        );

        verify(userRepository).findAll(pageable);
        verifyNoMoreInteractions(userRepository);
    }

    private static User createUser(String id, String name, String lastName, String user, String password, String avatar, String email) {
        User userDao = new User();

        userDao.setId(id);
        userDao.setName(name);
        userDao.setLastName(lastName);
        userDao.setUser(user);
        userDao.setPassword(password);
        userDao.setAvatar(avatar);
        userDao.setEmail(email);

        return userDao;
    }

    private static UserDto createUserDto(String id, String name, String lastName, String user, String password, String avatar, String email) {
        UserDto userDto = new UserDto();

        userDto.setId(id);
        userDto.setName(name);
        userDto.setLastName(lastName);
        userDto.setUser(user);
        userDto.setPassword(password);
        userDto.setAvatar(avatar);
        userDto.setEmail(email);

        return userDto;
    }
}
