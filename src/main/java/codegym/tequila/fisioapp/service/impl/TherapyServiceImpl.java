package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.model.Therapy;
import codegym.tequila.fisioapp.repository.TherapyRepository;
import codegym.tequila.fisioapp.repository.UserRepository;
import codegym.tequila.fisioapp.service.TherapyService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.util.Assert.notNull;

@Service
public class TherapyServiceImpl implements TherapyService {

    private final TherapyRepository therapyRepository;
    private final UserRepository userRepository;

    public TherapyServiceImpl(TherapyRepository therapyRepository, UserRepository userRepository) {
        this.therapyRepository = therapyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TherapyDto createTherapy(TherapyDto therapyDto) {
        Therapy therapy = new Therapy();

        therapy.setId(UUID.randomUUID().toString());
        therapy.setName(therapyDto.getName());
        therapy.setDescription(therapyDto.getDescription());
        therapy.setActive(true);

        return convertTherapyToDto(therapyRepository.save(therapy));
    }

    @Override
    public TherapyDto updateTherapy(String therapyId, TherapyDto therapyDto) {

        Therapy therapy = therapyRepository.findById(therapyId).orElse(null);
        if (therapy == null) {
            return null;
        } else {

            if (therapyDto.getName() != null) {
                therapy.setName(therapyDto.getName());
            }
            if (therapyDto.getDescription() != null) {
                therapy.setDescription(therapyDto.getDescription());
            }

            return convertTherapyToDto(therapyRepository.save(therapy));
        }

    }

    @Override
    public void deactivateTherapy(String therapyId) {
        Therapy therapy = therapyRepository.findById(therapyId).orElse(null);
        if (therapy != null) {
            therapy.setActive(false);
            therapyRepository.save(therapy);
        }
    }

    @Override
    public void activateTherapy(String therapyId) {
        Therapy therapy = therapyRepository.findById(therapyId).orElse(null);
        if (therapy != null) {
            therapy.setActive(true);
            therapyRepository.save(therapy);
        }
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
    public List<TherapyDto> getTherapies(Integer pageSize, Integer pageIndex, Boolean all, Boolean inactive) {
        return therapyRepository.findAll().stream()
                .map(TherapyServiceImpl::convertTherapyToDto)
                .collect(Collectors.toList());
    }

    private static TherapyDto convertTherapyToDto(Therapy therapy) {
        TherapyDto therapyDto = new TherapyDto();
        therapyDto.setId(therapy.getId());
        therapyDto.setName(therapy.getName());
        therapyDto.setDescription(therapy.getDescription());
        return therapyDto;
    }
}
