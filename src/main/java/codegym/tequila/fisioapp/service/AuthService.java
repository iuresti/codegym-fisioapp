package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.AuthResponse;
import codegym.tequila.fisioapp.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public interface AuthService {
AuthResponse login(LoginRequest request);
}
