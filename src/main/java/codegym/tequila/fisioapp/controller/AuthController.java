package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.AuthResponse;
import codegym.tequila.fisioapp.dto.LoginRequest;
import codegym.tequila.fisioapp.service.AuthService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(authService.login(request));
    }
@PostMapping("/registrer")
    public ResponseEntity<AuthResponse> registrer(){

        return ResponseEntity.ok(new AuthResponse());
    }
}
