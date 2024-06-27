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

    @GetMapping("/{Id}/medical-record")
    public ResponseEntity<MedicalRecord> getPatientMedicalRecord(@PathVariable String Id) {
        MedicalRecord medicalRecord = patientService.getMedicalRecordForPatient(Id);
        return ResponseEntity.ok(medicalRecord);
    }

    @PostMapping
    Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }
}