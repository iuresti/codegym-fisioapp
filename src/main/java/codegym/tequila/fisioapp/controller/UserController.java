package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
        long startTime = System.currentTimeMillis();
        logger.info("Get Users request");

        List<UserDto> users = userService.getUsers();

        logger.info("Get Users completed successfully in {} ms", (System.currentTimeMillis() - startTime));

        return users;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        long startTime = System.currentTimeMillis();

        logger.info("Update User {} request: {}", id, userDto);

        userDto.setId(id);

        ResponseEntity<UserDto> userDtoResponseEntity = new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);

        logger.info("Update Users completed successfully in {} ms", (System.currentTimeMillis() - startTime));

        return userDtoResponseEntity;
    }
}
