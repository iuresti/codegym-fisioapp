package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.MedicalRecordDto;
import codegym.tequila.fisioapp.service.MedicalRecordService;
import codegym.tequila.fisioapp.service.impl.MedicalRecordServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-record")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordServiceImpl medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public MedicalRecordDto createMedicalRecord(@RequestBody MedicalRecordDto medicalRecordDto) {
        return medicalRecordService.createMedicalRecord(medicalRecordDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDto> updateMedicalRecord(@PathVariable String id, @RequestBody MedicalRecordDto medicalRecordDto) {

        medicalRecordDto.setId(id);

        return new ResponseEntity<>(medicalRecordService.updateMedicalRecord(medicalRecordDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public MedicalRecordDto findById(@PathVariable String id) {
        return medicalRecordService.findById(id);
    }

}
