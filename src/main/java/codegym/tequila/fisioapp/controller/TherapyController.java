package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.service.TherapyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/therapy")
public class TherapyController {

    private static final Logger logger = LoggerFactory.getLogger(TherapyController.class);

    private final TherapyService therapyService;

    public TherapyController(TherapyService therapyService) {
        this.therapyService = therapyService;
    }

    @PostMapping
    public ResponseEntity<TherapyDto> createTherapy(@RequestBody TherapyDto therapyDto) {
        long startTime = System.currentTimeMillis();

        logger.info("createTherapy was called: {}.", therapyDto);

        TherapyDto result = therapyService.createTherapy(therapyDto);

        logger.info("createTherapy completed successfully in {} ms: {}.", System.currentTimeMillis() - startTime, result);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TherapyDto> updateTherapy(@PathVariable("id") String id, @RequestBody TherapyDto therapyDto) {
        long startTime = System.currentTimeMillis();

        logger.info("updateTherapy for {} was called: {}.", id, therapyDto);

        TherapyDto result = therapyService.updateTherapy(id, therapyDto);

        logger.info("updateTherapy completed successfully in {} ms: {}.", System.currentTimeMillis() - startTime, result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public void deactivateTherapy(@PathVariable("id") String id) {
        long startTime = System.currentTimeMillis();

        logger.info("deactivateTherapy for {} was called.", id);

        therapyService.deactivateTherapy(id);

        logger.info("deactivateTherapy for {} completed successfully in {} ms.", id, System.currentTimeMillis() - startTime);
    }

    @PutMapping("/{id}/activate")
    public void activateTherapy(@PathVariable("id") String id) {
        long startTime = System.currentTimeMillis();

        logger.info("activateTherapy for {} was called.", id);

        therapyService.activateTherapy(id);

        logger.info("activateTherapy for {} completed successfully in {} ms.", id, System.currentTimeMillis() - startTime);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TherapyDto> getTherapyById(@PathVariable String id) {
        long startTime = System.currentTimeMillis();

        logger.info("getTherapyById for {} was called.", id);

        TherapyDto therapyDto = therapyService.getTherapy(id);

        logger.info("getTherapyById for {} completed successfully in {} ms.", id, (System.currentTimeMillis() - startTime));

        return new ResponseEntity<>(therapyDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TherapyDto>> getTherapies(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) Boolean all,
            @RequestParam(required = false) Boolean inactive
    ) {
        long startTime = System.currentTimeMillis();

        logger.info("getTherapies was called with: pageSize={},pageIndex={},all={},inactive={}.", pageSize, pageIndex, all, inactive);

        List<TherapyDto> therapies = therapyService.getTherapies(pageSize, pageIndex, all, inactive);

        logger.info("getTherapies completed successfully in {} ms.", (System.currentTimeMillis() - startTime));

        return new ResponseEntity<>(therapies, HttpStatus.OK);
    }
}
