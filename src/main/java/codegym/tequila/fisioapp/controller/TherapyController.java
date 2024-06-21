package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.model.Therapy;
import codegym.tequila.fisioapp.service.TherapyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/therapy")
public class TherapyController {

    private TherapyService therapyService;

    public TherapyController(TherapyService therapyService) {
        this.therapyService = therapyService;
    }

    @PostMapping
    public TherapyDto createTherapy(@RequestBody TherapyDto therapyDto) {
        return therapyService.createTherapy(therapyDto);
    }

    @GetMapping("/{id}")
    public TherapyDto getTherapyById(@PathVariable String id) {
        return therapyService.getTherapy(id);
    }



}
