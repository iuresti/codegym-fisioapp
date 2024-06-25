package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto createUser(UserDto userDto);

    Page<UserDto> getUsers(Pageable pageable);

    UserDto updateUser(UserDto userDto);
}
