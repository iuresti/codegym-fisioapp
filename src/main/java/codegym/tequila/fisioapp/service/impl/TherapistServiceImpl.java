package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.TherapistDto;
import codegym.tequila.fisioapp.model.Therapist;
import codegym.tequila.fisioapp.repository.TherapistRepository;
import codegym.tequila.fisioapp.service.TherapistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TherapistServiceImpl implements TherapistService {

    private final TherapistRepository therapistRepository;
    public TherapistServiceImpl(TherapistRepository therapistRepository) {

        this.therapistRepository = therapistRepository;
    }

    @Override
    public TherapistDto createTherapist(TherapistDto therapistDto) {

        Therapist therapist = new Therapist();

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
    public List<TherapistDto> getTherapists() {
        return therapistRepository.findAll().stream().map(therapist -> {
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
        }).collect(Collectors.toList());
    }


}
