package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.TherapyDto;

import java.util.List;

public interface TherapyService {
    TherapyDto createTherapy(TherapyDto terapyDto);

    TherapyDto updateTherapy(String therapyId, TherapyDto therapyDto);

    void activateTherapy(String therapyId);

    void deactivateTherapy(String therapyId);

    TherapyDto getTherapy(String therapyId);

    List<TherapyDto> getTherapies(Integer pageSize, Integer pageIndex, Boolean all, Boolean inactive);
}
