package codegym.tequila.fisioapp.service;

import codegym.tequila.fisioapp.dto.TherapistDto;
import codegym.tequila.fisioapp.dto.UserDto;

import java.util.List;

public interface TherapistService {

    TherapistDto createTherapist(TherapistDto therapistDto);

    List<TherapistDto> getTherapists();
}
