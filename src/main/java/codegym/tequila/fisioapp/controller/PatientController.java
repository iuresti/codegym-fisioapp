package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.model.MedicalRecord;
import codegym.tequila.fisioapp.model.Patient;
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
    public ResponseEntity<MedicalRecord> getPatientMedicalRecord(@PathVariable String id) {
        MedicalRecord medicalRecord = patientService.getMedicalRecordForPatient(id);
        return ResponseEntity.ok(medicalRecord);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }
}