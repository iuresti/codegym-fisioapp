package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.TherapistDto;
import codegym.tequila.fisioapp.model.Therapist;
import codegym.tequila.fisioapp.repository.TherapistRepository;
import codegym.tequila.fisioapp.service.TherapistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
public class TherapistServiceImpl implements TherapistService {

    private final TherapistRepository therapistRepository;
    public TherapistServiceImpl(TherapistRepository therapistRepository) {

        this.therapistRepository = therapistRepository;
    }

    @Override
    public TherapistDto createTherapist(TherapistDto therapistDto) {

        Therapist therapist = new Therapist();

        therapist.setId(UUID.randomUUID().toString());
        therapist.setFirstname(therapistDto.getFirstname());
        therapist.setLastName(therapistDto.getLastName());
        therapist.setBirthDate(therapistDto.getBirthDate());
        therapist.setGender(therapistDto.getGender());
        therapist.setPhone(therapistDto.getPhone());
        therapist.setAddress(therapistDto.getAddress());
        therapist.setSpecialties(therapistDto.getSpecialties());

        therapistRepository.save(therapist);
        therapistDto.setId(therapist.getId());
        return therapistDto;
    }

    @Override
    public Page<TherapistDto> getTherapists(Pageable pageable) {

        Page<Therapist> therapistPage = therapistRepository.findAll(pageable);
        return therapistPage.map(TherapistServiceImpl::convertTherapistToDto);
    }

    @Override
    public TherapistDto getTherapistById(String id) {
       Therapist therapist = therapistRepository
               .findById(id).orElseThrow(() -> new NoSuchElementException("Therapist " + id + " not found"));
       return convertTherapistToDto(therapist);
    }

    @Override
    public TherapistDto updateTherapist(TherapistDto therapistDto) {
        Therapist therapist = therapistRepository.findById(therapistDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Therapist " + therapistDto.getId() + " not found"));

        if (StringUtils.hasLength(therapistDto.getFirstname())) {
            therapist.setFirstname(therapistDto.getFirstname());
        }

        if (StringUtils.hasLength(therapistDto.getLastName())) {
            therapist.setLastName(therapistDto.getLastName());
        }

        if (therapistDto.getBirthDate() != null) {
            therapist.setBirthDate(therapistDto.getBirthDate());
        }

        if (StringUtils.hasLength(therapistDto.getGender())) {
            therapist.setGender(therapistDto.getGender());
        }

        if (StringUtils.hasLength(therapistDto.getPhone())) {
            therapist.setPhone(therapistDto.getPhone());
        }

        if (StringUtils.hasLength(therapistDto.getAddress())) {
            therapist.setAddress(therapistDto.getAddress());
        }

        if (StringUtils.hasLength(therapistDto.getSpecialties())) {
            therapist.setSpecialties(therapistDto.getSpecialties());
        }
        return convertTherapistToDto(therapistRepository.save(therapist));
    }

    @Override
    public TherapistDto deleteTherapist(String id) {
        Therapist therapist = therapistRepository
                .findById(id).orElseThrow(() -> new NoSuchElementException("Therapist " + id + " not found"));

        therapistRepository.delete(therapist);

        return convertTherapistToDto(therapist);
    }

    private static TherapistDto convertTherapistToDto(Therapist therapist) {
        return getTherapistDto(therapist);
    }

    private static TherapistDto getTherapistDto(Therapist therapist) {
        TherapistDto therapistDto = new TherapistDto();
        therapistDto.setId(therapist.getId());
        therapistDto.setFirstname(therapist.getFirstname());
        therapistDto.setLastName(therapist.getLastName());
        therapistDto.setBirthDate(therapist.getBirthDate());
        therapistDto.setGender(therapist.getGender());
        therapistDto.setPhone(therapist.getPhone());
        therapistDto.setAddress(therapist.getAddress());
        therapistDto.setSpecialties(therapist.getSpecialties());
        return therapistDto;
    }
}
