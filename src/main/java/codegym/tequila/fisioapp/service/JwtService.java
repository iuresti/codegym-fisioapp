package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.UserDto;

public interface JwtService {
    String getToken(UserDto userDto);
}
