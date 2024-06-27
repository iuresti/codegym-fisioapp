package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.model.Therapy;
import codegym.tequila.fisioapp.repository.TherapyRepository;
import codegym.tequila.fisioapp.service.TherapyService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TherapyServiceImpl implements TherapyService {

    private final TherapyRepository therapyRepository;

    public TherapyServiceImpl(TherapyRepository therapyRepository) {
        this.therapyRepository = therapyRepository;
    }

    @Override
    public TherapyDto createTherapy(TherapyDto therapyDto) {

        if (!StringUtils.hasLength(therapyDto.getName())) {
            throw new IllegalArgumentException("Therapy must have a name");
        }

        if (!StringUtils.hasLength(therapyDto.getDescription())) {
            throw new IllegalArgumentException("Therapy must have a description");
        }

        Therapy therapy = new Therapy();

        therapy.setId(UUID.randomUUID().toString());
        therapy.setName(therapyDto.getName());
        therapy.setDescription(therapyDto.getDescription());
        therapy.setActive(true);

        return convertTherapyToDto(therapyRepository.save(therapy));
    }

    @Transactional
    @Override
    public TherapyDto updateTherapy(String therapyId, TherapyDto therapyDto) {

        Therapy therapy = therapyRepository.findById(therapyId)
                .orElseThrow(() -> new NoSuchElementException("Therapy " + therapyId + " not found"));

        if (StringUtils.hasLength(therapyDto.getName())) {
            therapy.setName(therapyDto.getName());
        }

        if (StringUtils.hasLength(therapyDto.getDescription())) {
            therapy.setDescription(therapyDto.getDescription());
        }

        return convertTherapyToDto(therapyRepository.save(therapy));
    }

    @Override
    public void deactivateTherapy(String therapyId) {
        Therapy therapy = therapyRepository.findById(therapyId)
                .orElseThrow(() -> new NoSuchElementException("Therapy " + therapyId + " not found"));

        therapy.setActive(false);
        therapyRepository.save(therapy);
    }

    @Override
    public void activateTherapy(String therapyId) {
        Therapy therapy = therapyRepository.findById(therapyId)
                .orElseThrow(() -> new NoSuchElementException("Therapy " + therapyId + " not found"));

        therapy.setActive(true);
        therapyRepository.save(therapy);
    }


    @Override
    public TherapyDto getTherapy(String therapyId) {
        Therapy therapy = therapyRepository
                .findById(therapyId).orElseThrow(() -> new NoSuchElementException("Therapy " + therapyId + " not found"));

        return convertTherapyToDto(therapy);
    }

    @Override
    public List<TherapyDto> getTherapies(Integer pageSize, Integer pageIndex, Boolean all, Boolean inactive) {

        pageSize = pageSize == null ? 10 : pageSize;
        pageIndex = pageIndex == null ? 0 : pageIndex;

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        List<Therapy> therapiesList;

        if (Boolean.TRUE.equals(all)) {
            therapiesList = therapyRepository.findAll(pageable).getContent();
        } else if (Boolean.TRUE.equals(inactive)) {
            therapiesList = therapyRepository.findAllByActive(false, pageable);
        } else {
            therapiesList = therapyRepository.findAllByActive(true, pageable);
        }

        return therapiesList.stream()
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
