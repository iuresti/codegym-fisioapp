package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.config.UserDetailsImpl;
import codegym.tequila.fisioapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(UserServiceImpl.convertUserToDto(userRepository.findByUser(username).orElseThrow(()->new NoSuchElementException("User not found"))));
    }
}
