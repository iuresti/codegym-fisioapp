package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.AuthResponse;
import codegym.tequila.fisioapp.dto.LoginRequest;
import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.model.User;
import codegym.tequila.fisioapp.repository.UserRepository;
import codegym.tequila.fisioapp.service.AuthService;
import codegym.tequila.fisioapp.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthServiceImpl(JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUser(request.getUsername()).orElseThrow(() -> new NoSuchElementException("User not found"));
        String token = jwtService.getToken(user);
        return new AuthResponse(token);
    }
}
