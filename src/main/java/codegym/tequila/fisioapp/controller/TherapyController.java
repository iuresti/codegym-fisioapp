package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.service.TherapyService;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/therapy")
@CrossOrigin
public class TherapyController {

    private final TherapyService therapyService;

    public TherapyController(TherapyService therapyService) {
        this.therapyService = therapyService;
    }

    @PostMapping
    public ResponseEntity<TherapyDto> createTherapy(@RequestBody TherapyDto therapyDto) {

        TherapyDto result = therapyService.createTherapy(therapyDto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TherapyDto> updateTherapy(@PathVariable("id") String id, @RequestBody TherapyDto therapyDto) {

        TherapyDto result = therapyService.updateTherapy(id, therapyDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
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

        TherapyDto therapyDto = therapyService.getTherapy(id);

        return new ResponseEntity<>(therapyDto, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<TherapyDto>> getTherapies(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) Boolean all,
            @RequestParam(required = false) Boolean inactive
    ) {

        List<TherapyDto> therapies = therapyService.getTherapies(pageSize, pageIndex, all, inactive);

        return new ResponseEntity<>(therapies, HttpStatus.OK);
    }
}
