package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.model.MedicalRecord;
import codegym.tequila.fisioapp.model.Patient;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {

    MedicalRecord getMedicalRecordForPatient(String patientId);

    Patient createPatient (Patient patient);
}
