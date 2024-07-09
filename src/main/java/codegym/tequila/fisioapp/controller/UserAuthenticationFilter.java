package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.exception.InvalidCredentialsException;
import codegym.tequila.fisioapp.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class UserAuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationFilter.class);

    private final UserService userService;

    public UserAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("UserAuthFilter");

        String authorizationHeader = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        if(authorizationHeader == null){
            ((HttpServletResponse)(servletResponse)).setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String headerAuthorization = authorizationHeader.substring("Basic ".length());

        byte[] decodedBytes = Base64.getDecoder().decode(headerAuthorization);

        String []authValue = new String(decodedBytes, StandardCharsets.UTF_8).split(":");

        try{
            userService.validateUserExist(authValue[0], authValue[1]);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (InvalidCredentialsException ex){
            ((HttpServletResponse)(servletResponse)).setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        logger.info("UserAuthFilter termina");
    }
}
