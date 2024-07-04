package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.MedicalRecordDto;
import codegym.tequila.fisioapp.model.MedicalRecord;


public interface MedicalRecordService {
    MedicalRecordDto createMedicalRecord(MedicalRecordDto medicalRecordDto);

    MedicalRecordDto updateMedicalRecord(MedicalRecordDto medicalRecordDto);

    MedicalRecordDto findById(String id);
}
