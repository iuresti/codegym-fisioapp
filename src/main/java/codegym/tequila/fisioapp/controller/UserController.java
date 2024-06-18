package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto) {

        userDto.setId(id);

        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }
}
