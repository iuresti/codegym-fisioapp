package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.TherapistDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface TherapistService {

    TherapistDto createTherapist(TherapistDto therapistDto);

    Page<TherapistDto> getTherapists(Pageable pageable);

    TherapistDto getTherapistById(String id);

    TherapistDto updateTherapist(TherapistDto therapistDto);

    TherapistDto deleteTherapist(String id);
}
