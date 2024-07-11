package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.TherapistDto;
import codegym.tequila.fisioapp.model.Therapist;
import codegym.tequila.fisioapp.repository.TherapistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TherapistServiceImplTest {

    @Test
    void createdTherapistTest() {
        //Given
        TherapistRepository therapistRepository = mock(TherapistRepository.class);
        TherapistServiceImpl therapistService = new TherapistServiceImpl(therapistRepository);
        ArgumentCaptor<Therapist> therapistArgumentCaptor = ArgumentCaptor.forClass(Therapist.class);
        TherapistDto therapistDto = new TherapistDto();
        Therapist therapistDao = new Therapist();

        therapistDto.setId("id");
        therapistDto.setFirstname("firstName");
        therapistDto.setLastName("lastName");
        therapistDto.setBirthDate(LocalDate.of(1990, 1, 1));
        therapistDto.setGender("gender");
        therapistDto.setAddress("address");
        therapistDto.setSpecialties("specialties");

        BeanUtils.copyProperties(therapistDto, therapistDao);
        therapistDao.setId(UUID.randomUUID().toString());

        when(therapistRepository.save(therapistArgumentCaptor.capture())).thenReturn(therapistDao);

        //When
        TherapistDto therapistDtoReturned = therapistService.createTherapist(therapistDto);

        //Then
        Therapist savedTherapist = therapistArgumentCaptor.getValue();
        assertThat(savedTherapist.getId()).isNotNull();
        assertThat(savedTherapist.getFirstname()).isEqualTo(therapistDto.getFirstname());
        assertThat(savedTherapist.getLastName()).isEqualTo(therapistDto.getLastName());
        assertThat(savedTherapist.getBirthDate()).isEqualTo(therapistDto.getBirthDate().toString());
        assertThat(savedTherapist.getGender()).isEqualTo(therapistDto.getGender());
        assertThat(savedTherapist.getAddress()).isEqualTo(therapistDto.getAddress());
        assertThat(savedTherapist.getSpecialties()).isEqualTo(therapistDto.getSpecialties());

        therapistDto.setId(therapistDtoReturned.getId());
        assertThat(therapistDto).isEqualTo(therapistDtoReturned);

        verify(therapistRepository).save(savedTherapist);
        verifyNoMoreInteractions(therapistRepository);
    }

    @Test
    void updateTherapistTest(){
        //Given
        TherapistRepository therapistRepository = mock(TherapistRepository.class);
        TherapistServiceImpl therapistService = new TherapistServiceImpl(therapistRepository);
        ArgumentCaptor<Therapist> therapistArgumentCaptor = ArgumentCaptor.forClass(Therapist.class);
        TherapistDto therapistDto = new TherapistDto();

        therapistDto.setId(UUID.randomUUID().toString());
        therapistDto.setFirstname("firstName");
        therapistDto.setLastName("lastName");
        therapistDto.setBirthDate(LocalDate.of(1990,1,1));
        therapistDto.setGender("gender");
        therapistDto.setPhone("phone");
        therapistDto.setAddress("address");
        therapistDto.setSpecialties("specialties");

        Therapist therapistDao = new Therapist();
        therapistDao.setId(therapistDto.getId());
        therapistDao.setFirstname("A");
        therapistDao.setLastName("B");
        therapistDao.setBirthDate(LocalDate.of(1990,1,1));
        therapistDao.setGender("C");
        therapistDao.setPhone("D");
        therapistDao.setAddress("E");
        therapistDao.setSpecialties("F");

        when(therapistRepository.findById(therapistDto.getId())).thenReturn(Optional.of(therapistDao));
        when(therapistRepository.save(therapistArgumentCaptor.capture())).thenReturn(therapistDao);

        //When
        TherapistDto therapistDtoReturned = therapistService.updateTherapist(therapistDto);

        //Then
        Therapist savedTherapist = therapistArgumentCaptor.getValue();
        assertThat(savedTherapist.getId()).isEqualTo(therapistDto.getId());
        assertThat(savedTherapist.getFirstname()).isEqualTo(therapistDto.getFirstname());
        assertThat(savedTherapist.getLastName()).isEqualTo(therapistDto.getLastName());
        assertThat(savedTherapist.getBirthDate()).isEqualTo(therapistDto.getBirthDate());
        assertThat(savedTherapist.getGender()).isEqualTo(therapistDto.getGender());
        assertThat(savedTherapist.getPhone()).isEqualTo(therapistDto.getPhone());
        assertThat(savedTherapist.getAddress()).isEqualTo(therapistDto.getAddress());
        assertThat(savedTherapist.getSpecialties()).isEqualTo(therapistDto.getSpecialties());

        BeanUtils.copyProperties(therapistDao, therapistDto);
        assertThat(therapistDto).isEqualTo(therapistDtoReturned);

        verify(therapistRepository).findById(therapistDto.getId());
        verify(therapistRepository).save(savedTherapist);
        verifyNoMoreInteractions(therapistRepository);

    }

    @Test
    void getTherapistsTest(){
        //Given
        Pageable pageable = PageRequest.of(0,10);
        TherapistRepository therapistRepository = mock(TherapistRepository.class);
        TherapistServiceImpl therapistService = new TherapistServiceImpl(therapistRepository);

        Therapist therapist1 = new Therapist();
        therapist1.setId(UUID.randomUUID().toString());
        therapist1.setFirstname("Jesus");
        therapist1.setLastName("Ramirez");
        therapist1.setBirthDate(LocalDate.of(1991,2,2));
        therapist1.setGender("Male");
        therapist1.setPhone("123456789");
        therapist1.setAddress("Libertad # 90");
        therapist1.setSpecialties("Chiropractor");

        Therapist therapist2 = new Therapist();
        therapist2.setId(UUID.randomUUID().toString());
        therapist2.setFirstname("Rogelio");
        therapist2.setLastName("Diaz");
        therapist2.setBirthDate(LocalDate.of(1999,3,5));
        therapist2.setGender("Male");
        therapist2.setPhone("987654321");
        therapist2.setAddress("Reforma # 123");
        therapist2.setSpecialties("psychodynamics");

        List<Therapist> therapists = Arrays.asList(therapist1, therapist2);
        Page<Therapist> therapistPage = new PageImpl<>(therapists, pageable, therapists.size());

        when(therapistRepository.findAll(pageable)).thenReturn(therapistPage);

        //When
        Page<TherapistDto> result = therapistService.getTherapists(pageable);

        //Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getFirstname()).isEqualTo("Jesus");
        assertThat(result.getContent().get(1).getFirstname()).isEqualTo("Rogelio");

        verify(therapistRepository).findAll(pageable);
        verifyNoMoreInteractions(therapistRepository);

    }

    @Test
    void getTherapistByIdTest() {
        //Given
        TherapistRepository therapistRepository = mock(TherapistRepository.class);
        TherapistServiceImpl therapistService = new TherapistServiceImpl(therapistRepository);
        String id = UUID.randomUUID().toString();
        Therapist therapist = new Therapist();
        therapist.setId(id);
        therapist.setFirstname("Jesus");
        therapist.setLastName("Ramirez");
        therapist.setBirthDate(LocalDate.of(1991,2,2));
        therapist.setGender("Male");
        therapist.setPhone("123456789");
        therapist.setAddress("Libertad # 90");
        therapist.setSpecialties("Chiropractor");

        when(therapistRepository.findById(id)).thenReturn(Optional.of(therapist));

        //When
        TherapistDto result = therapistService.getTherapistById(id);

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getFirstname()).isEqualTo("Jesus");
        assertThat(result.getLastName()).isEqualTo("Ramirez");

        verify(therapistRepository).findById(id);
        verifyNoMoreInteractions(therapistRepository);

    }

    @Test
    void getTherapistByIdNotFoundTest() {
        //Given
        TherapistRepository therapistRepository = mock(TherapistRepository.class);
        TherapistServiceImpl therapistService = new TherapistServiceImpl(therapistRepository);
        String id = UUID.randomUUID().toString();

        when(therapistRepository.findById(id)).thenReturn(Optional.empty());

        //When & Then
        Exception exception = Assertions.assertThrows(NoSuchElementException.class,
                () -> therapistService.getTherapistById(id));

        assertThat(exception.getMessage()).isEqualTo("Therapist " + id + " not found");

        verify(therapistRepository).findById(id);
        verifyNoMoreInteractions(therapistRepository);

    }

    @Test
    void deleteTherapistTest() {
        // Given
        TherapistRepository therapistRepository = mock(TherapistRepository.class);
        TherapistServiceImpl therapistService = new TherapistServiceImpl(therapistRepository);
        String id = UUID.randomUUID().toString();

        Therapist therapist = new Therapist();
        therapist.setId(id);
        therapist.setFirstname("Jesus");
        therapist.setLastName("Ramirez");
        therapist.setBirthDate(LocalDate.of(1991, 2, 2));
        therapist.setGender("Male");
        therapist.setPhone("123456789");
        therapist.setAddress("Libertad # 90");
        therapist.setSpecialties("Chiropractor");

        when(therapistRepository.findById(id)).thenReturn(Optional.of(therapist));

        ArgumentCaptor<Therapist> therapistArgumentCaptor = ArgumentCaptor.forClass(Therapist.class);

        // When
        TherapistDto result = therapistService.deleteTherapist(id);

        // Then
        verify(therapistRepository).findById(id);
        verify(therapistRepository).delete(therapistArgumentCaptor.capture());
        verifyNoMoreInteractions(therapistRepository);

        Therapist deletedTherapist = therapistArgumentCaptor.getValue();
        assertThat(deletedTherapist).isEqualTo(therapist);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getFirstname()).isEqualTo("Jesus");
        assertThat(result.getLastName()).isEqualTo("Ramirez");
    }


    @Test
    void deleteTherapistNotFoundTest() {
        //Given
        TherapistRepository therapistRepository = mock(TherapistRepository.class);
        TherapistServiceImpl therapistService = new TherapistServiceImpl(therapistRepository);
        String id = UUID.randomUUID().toString();

        when(therapistRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(NoSuchElementException.class,
                () -> therapistService.deleteTherapist(id));

        assertThat(exception.getMessage()).isEqualTo("Therapist " + id + " not found");

        verify(therapistRepository).findById(id);
        verify(therapistRepository, never()).delete(any(Therapist.class));
    }
}