package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.TherapistDto;
import codegym.tequila.fisioapp.service.TherapistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/therapist")
public class TherapistController {

    private final TherapistService therapistService;

    public TherapistController(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @PostMapping
    public TherapistDto createTherapist(@RequestBody TherapistDto therapistDto) {
        return therapistService.createTherapist(therapistDto);
    }

    @GetMapping
    public List<TherapistDto> getTherapists() {
        return therapistService.getTherapists();
    }
}
