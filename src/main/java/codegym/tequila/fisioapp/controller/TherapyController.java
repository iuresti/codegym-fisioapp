package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.service.TherapyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public TherapyDto updateTherapy(@PathVariable("id") String id, @RequestBody TherapyDto therapyDto) {
        return therapyService.updateTherapy(id, therapyDto);
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
    public TherapyDto getTherapyById(@PathVariable String id) {
        return therapyService.getTherapy(id);
    }

    @GetMapping
    public List<TherapyDto> getTherapys(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) Boolean all,
            @RequestParam(required = false) Boolean inactive
    ) {
        return therapyService.getTherapies(pageSize, pageIndex, all, inactive);
    }



}
