package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.model.User;
import codegym.tequila.fisioapp.repository.UserRepository;
import codegym.tequila.fisioapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setAvatar(userDto.getAvatar());
        user.setEmail(userDto.getEmail());
        user.setUser(userDto.getUser());
        user.setLastName(userDto.getLastName());
        user.setName(userDto.getName());
        user.setPassword(UUID.randomUUID().toString());

        userRepository.createUser(user);

        userDto.setId(user.getId());

        return userDto;
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.getUsers().stream()
                .map(user -> {
                    UserDto userDto = new UserDto();

                    userDto.setId(user.getId());
                    userDto.setAvatar(user.getAvatar());
                    userDto.setEmail(user.getEmail());
                    userDto.setUser(user.getUser());
                    userDto.setLastName(user.getLastName());
                    userDto.setName(user.getName());

                    return userDto;
                }).collect(Collectors.toList());
    }
}
