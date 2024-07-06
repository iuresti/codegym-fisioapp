package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.dto.MedicalRecordDto;
import codegym.tequila.fisioapp.dto.PatientDto;
import codegym.tequila.fisioapp.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}/medical-record")
    public ResponseEntity<MedicalRecordDto> getPatientMedicalRecord(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getMedicalRecordForPatient(id));
    }

    @PostMapping
    public PatientDto createPatient(@RequestBody PatientDto patientDto) {
        return patientService.createPatient(patientDto);
    }
}