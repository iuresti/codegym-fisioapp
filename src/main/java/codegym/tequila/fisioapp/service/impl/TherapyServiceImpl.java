package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.model.Therapy;
import codegym.tequila.fisioapp.repository.TherapyRepository;
import codegym.tequila.fisioapp.service.TherapyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TherapyServiceImpl implements TherapyService {

    private final TherapyRepository therapyRepository;

    public TherapyServiceImpl(TherapyRepository therapyRepository) {
        this.therapyRepository = therapyRepository;
    }

    @Override
    public TherapyDto createTherapy(TherapyDto therapyDto) {
        Therapy therapy = new Therapy();

        therapy.setId(UUID.randomUUID().toString());
        therapy.setName(therapyDto.getName());
        therapy.setDescription(therapyDto.getDescription());
        therapy.setActive(true);

        therapyRepository.save(therapy);

        therapyDto.setId(therapy.getId());
        return therapyDto;
    }

    @Override
    public TherapyDto getTherapy(String therapyId) {
        Therapy therapy = therapyRepository.findById(therapyId).orElse(null);
        if (therapy == null) {
            return null;
        } else {
            TherapyDto therapyDto = new TherapyDto();
            therapyDto.setId(therapy.getId());
            therapyDto.setName(therapy.getName());
            therapyDto.setDescription(therapy.getDescription());
            return therapyDto;
        }
    }



    @Override
    public TherapyDto updateTherapy(String therapyId, TherapyDto therapyDto) {
        return null;
    }

    @Override
    public void activateTherapy(String therapyId) {

    }

    @Override
    public void deactivateTherapy(String therapyId) {

    }

    @Override
    public List<TherapyDto> getTherapies() {
        return List.of();
    }
}
