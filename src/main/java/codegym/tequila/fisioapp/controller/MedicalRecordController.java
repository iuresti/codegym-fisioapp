package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.model.MedicalRecord;
import codegym.tequila.fisioapp.service.impl.MedicalRecordServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MedicalRecordController {
    private final MedicalRecordServiceImpl medicalRecordService;

    public MedicalRecordController(MedicalRecordServiceImpl medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PutMapping("/medical-record/{id}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable String id, @RequestBody MedicalRecord medicalRecord) {

        medicalRecord.setId(id);

        return new ResponseEntity<>(medicalRecordService.updateMedicalRecord(medicalRecord), HttpStatus.OK);
    }

    @GetMapping("/medical-record/{id}")
    public MedicalRecord findById(@PathVariable String id) {
        return medicalRecordService.findById(id);
    }

    @GetMapping("/patient/{id}/medical-record")
    public MedicalRecord findByPatientId(@PathVariable String id) {
        return medicalRecordService.findByPatientId(id);
    }
}
