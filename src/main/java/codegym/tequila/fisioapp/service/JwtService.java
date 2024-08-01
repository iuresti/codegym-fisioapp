package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String getToken(UserDetails user);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
