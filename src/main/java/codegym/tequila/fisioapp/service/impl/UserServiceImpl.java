package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.model.User;
import codegym.tequila.fisioapp.repository.UserRepository;
import codegym.tequila.fisioapp.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {

        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setAvatar(userDto.getAvatar());
        user.setEmail(userDto.getEmail());
        user.setUser(userDto.getUser());
        user.setLastName(userDto.getLastName());
        user.setName(userDto.getName());
        user.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);

        userDto.setId(user.getId());

        return userDto;
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserServiceImpl::convertUserToDto);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NoSuchElementException("User " + userDto.getId() + " not found"));

        if (!StringUtils.isEmpty(userDto.getName())) {
            user.setName(userDto.getName());
        }

        if (!StringUtils.isEmpty(userDto.getLastName())) {
            user.setLastName(userDto.getLastName());
        }

        if (!StringUtils.isEmpty(userDto.getAvatar())) {
            user.setAvatar(userDto.getAvatar());
        }

        if (!StringUtils.isEmpty(userDto.getEmail())) {
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
