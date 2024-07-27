package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.model.User;
import codegym.tequila.fisioapp.repository.UserRepository;
import codegym.tequila.fisioapp.service.EmailService;
import codegym.tequila.fisioapp.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    private UserDetailsService userDetailsService;


    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
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

        emailService.sendSimpleEmail(user.getEmail(), "User created",
                String.format("Tu usuario %s ha sido creado", user.getUser()));

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

        BeanUtils.copyProperties(user, userDto);
        userDto.setPassword(null);

        return userDto;
    }
}
