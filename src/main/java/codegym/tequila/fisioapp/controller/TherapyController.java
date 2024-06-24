package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.service.TherapyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/therapy")
public class TherapyController {

    private final TherapyService therapyService;

    public TherapyController(TherapyService therapyService) {
        this.therapyService = therapyService;
    }

    @PostMapping
    public ResponseEntity<TherapyDto> createTherapy(@RequestBody TherapyDto therapyDto) {
        return new ResponseEntity<>(therapyService.createTherapy(therapyDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TherapyDto> updateTherapy(@PathVariable("id") String id, @RequestBody TherapyDto therapyDto) {
        return new ResponseEntity<>(therapyService.updateTherapy(id, therapyDto), HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public void deactivateTherapy(@PathVariable("id") String id) {
        therapyService.deactivateTherapy(id);
    }

    @PutMapping("/{id}/activate")
    public void activateTherapy(@PathVariable("id") String id) {
        therapyService.activateTherapy(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TherapyDto> getTherapyById(@PathVariable String id) {
        return new ResponseEntity<>(therapyService.getTherapy(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TherapyDto>> getTherapys(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) Boolean all,
            @RequestParam(required = false) Boolean inactive
    ) {
        return new ResponseEntity<>(therapyService.getTherapies(pageSize, pageIndex, all, inactive), HttpStatus.OK);
    }
}
