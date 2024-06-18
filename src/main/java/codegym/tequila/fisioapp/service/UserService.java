package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getUsers();
}
