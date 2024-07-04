package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.MedicalRecordDto;
import codegym.tequila.fisioapp.dto.PatientDto;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {

    MedicalRecordDto getMedicalRecordForPatient(String patientId);

    PatientDto createPatient (PatientDto patientDto);
}
