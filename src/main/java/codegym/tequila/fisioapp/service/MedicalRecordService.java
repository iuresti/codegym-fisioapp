package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.MedicalRecordDto;



public interface MedicalRecordService {
    MedicalRecordDto createMedicalRecord(MedicalRecordDto medicalRecordDto);

    MedicalRecordDto updateMedicalRecord(MedicalRecordDto medicalRecordDto);

    MedicalRecordDto findById(String id);
}
