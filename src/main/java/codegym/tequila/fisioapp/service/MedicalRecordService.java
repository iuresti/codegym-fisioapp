package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.model.MedicalRecord;


public interface MedicalRecordService {
    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord);

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

    MedicalRecord findById(String id);

    MedicalRecord findByPatientId(String id);
}
