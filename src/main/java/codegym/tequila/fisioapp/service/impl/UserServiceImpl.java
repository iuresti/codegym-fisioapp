package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.model.User;
import codegym.tequila.fisioapp.repository.UserRepository;
import codegym.tequila.fisioapp.service.UserService;
import io.micrometer.common.util.StringUtils;
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
        user.setPassword(UUID.randomUUID().toString().substring(0, 15));

        userRepository.save(user);

        userDto.setId(user.getId());

        return userDto;
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserServiceImpl::convertUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow();

        if(!StringUtils.isEmpty(userDto.getName())){
            user.setName(userDto.getName());
        }

        if(!StringUtils.isEmpty(userDto.getLastName())){
            user.setLastName(userDto.getLastName());
        }

        if(!StringUtils.isEmpty(userDto.getAvatar())){
            user.setAvatar(userDto.getAvatar());
        }

        if(!StringUtils.isEmpty(userDto.getEmail())){
            user.setEmail(userDto.getEmail());
        }

        return convertUserToDto(userRepository.save(user));
    }

    private static UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setAvatar(user.getAvatar());
        userDto.setEmail(user.getEmail());
        userDto.setUser(user.getUser());
        userDto.setLastName(user.getLastName());
        userDto.setName(user.getName());

        return userDto;
    }
}
