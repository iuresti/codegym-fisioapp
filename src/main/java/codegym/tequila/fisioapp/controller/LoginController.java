package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.LoggedUserInfo;
import codegym.tequila.fisioapp.dto.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final AuthenticationManager authenticationManager;


    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/auth/login")
    public ResponseEntity<LoggedUserInfo> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // NOTA: El objeto "authentication" tiene un método getPrincipal y otro getDetails que pueden
        // ser utilizados para devolver información del usuario logueado. Eso se hace junto con la personalización
        // del UserDetailsService

        LoggedUserInfo loggedUserInfo = new LoggedUserInfo();

        loggedUserInfo.setName("Un usuario");
        //El objeto loggedUserInfo se llena con información útil para personalizar al usuario su landing page


        return new ResponseEntity<>(loggedUserInfo, HttpStatus.OK);
    }

    @PostMapping("/auth/logout")
    public void logout() {

        logger.info("logout");
    }
}
