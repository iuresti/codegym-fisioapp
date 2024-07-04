package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.TherapistDto;
import codegym.tequila.fisioapp.service.TherapistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/therapist")
public class TherapistController {

    private static final Logger logger =  LoggerFactory.getLogger(TherapistController.class);
    private final TherapistService therapistService;

    public TherapistController(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @PostMapping
    public TherapistDto createTherapist(@RequestBody TherapistDto therapistDto) {
        return therapistService.createTherapist(therapistDto);
    }

    @GetMapping
    public Page<TherapistDto> getTherapists(
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "1") int pageIndex){
        long startTime = System.currentTimeMillis();
        logger.info("Get Therapists request");

        Pageable pageable = PageRequest.of(pageIndex -1, pageSize );

        logger.info("Get Therapists completed successfully in {} ms", (System.currentTimeMillis() - startTime));

        return therapistService.getTherapists(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TherapistDto> getTherapistById(@PathVariable String id){
        long startTime = System.currentTimeMillis();
        logger.info("Get Therapists {} by id  request", id);

        TherapistDto therapistDto = therapistService.getTherapistById(id);

        logger.info("Get Therapists by id completed successfully in {} ms", (System.currentTimeMillis() - startTime));

        return new ResponseEntity<>(therapistDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TherapistDto> updateTherapist(@PathVariable String id,@RequestBody TherapistDto therapistDto){
        long startTime = System.currentTimeMillis();
        logger.info("update Therapist {} request: {}",id, therapistDto);

        therapistDto.setId(id);

        ResponseEntity<TherapistDto> therapistDtoResponseEntity = new ResponseEntity<>(therapistService.updateTherapist(therapistDto),HttpStatus.OK);

        logger.info("update Therapists completed successfully in {} ms", (System.currentTimeMillis() - startTime));

        return therapistDtoResponseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTherapist(@PathVariable String id){
        long startTime = System.currentTimeMillis();
        logger.info("Delete Therapist {} request:", id);

        therapistService.deleteTherapist(id);

        logger.info("update Therapists completed successfully in {} ms", (System.currentTimeMillis() - startTime));


        return ResponseEntity.noContent().build();
    }
}
