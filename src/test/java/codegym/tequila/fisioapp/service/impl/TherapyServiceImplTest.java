package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.dto.TherapyDto;
import codegym.tequila.fisioapp.model.Therapy;
import codegym.tequila.fisioapp.repository.TherapyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class TherapyServiceImplTest {

    @Test
    void createTherapy_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);
        ArgumentCaptor<Therapy> therapyArgumentCaptor = ArgumentCaptor.forClass(Therapy.class);

        TherapyDto therapyDto = new TherapyDto();
        therapyDto.setName("Test therapy");
        therapyDto.setDescription("This is the description of new test Therapy");

        Therapy therapyDao = new Therapy();
        BeanUtils.copyProperties(therapyDto, therapyDao);
        therapyDao.setId(UUID.randomUUID().toString());
        therapyDao.setActive(true);

        when(therapyRepository.save(therapyArgumentCaptor.capture())).thenReturn(therapyDao);

        //When
        TherapyDto therapyDtoReturned = therapyService.createTherapy(therapyDto);

        //Then
        Therapy savedTherapy = therapyArgumentCaptor.getValue();

        assertThat(savedTherapy.getId()).isNotNull();
        assertThat(savedTherapy.getName()).isEqualTo(therapyDto.getName());
        assertThat(savedTherapy.getDescription()).isEqualTo(therapyDto.getDescription());
        assertThat(savedTherapy.isActive()).isTrue();

        therapyDto.setId(therapyDtoReturned.getId());
        assertThat(therapyDto).isEqualTo(therapyDtoReturned);

        verify(therapyRepository).save(savedTherapy);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void createTherapyWithoutName_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        TherapyDto therapyDto = new TherapyDto();
        therapyDto.setName(null);
        therapyDto.setDescription("This is the description of new test Therapy");

        //When
        assertThatThrownBy(() -> therapyService.createTherapy(therapyDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Therapy must have a name");

        //Then
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void createTherapyWithoutDescription_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        TherapyDto therapyDto = new TherapyDto();
        therapyDto.setName("Test therapy");
        therapyDto.setDescription(null);

        //When
        assertThatThrownBy(() -> therapyService.createTherapy(therapyDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Therapy must have a description");

        //Then
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void updateTherapy_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);
        ArgumentCaptor<Therapy> therapyArgumentCaptor = ArgumentCaptor.forClass(Therapy.class);

        String therapyDtoId = UUID.randomUUID().toString();

        TherapyDto therapyDto = new TherapyDto();
        therapyDto.setName("Test therapy");
        therapyDto.setDescription("This is the description of new test Therapy");

        Therapy therapyDao = new Therapy();
        therapyDao.setId(therapyDtoId);
        therapyDao.setName("X");
        therapyDao.setDescription("Y");
        therapyDao.setActive(true);

        when(therapyRepository.findById(therapyDtoId)).thenReturn(Optional.of(therapyDao));
        when(therapyRepository.save(therapyArgumentCaptor.capture())).thenReturn(therapyDao);

        //When
        TherapyDto therapyDtoReturned = therapyService.updateTherapy(therapyDtoId, therapyDto);

        //Then
        Therapy savedTherapy = therapyArgumentCaptor.getValue();

        assertThat(savedTherapy.getId()).isEqualTo(therapyDao.getId());
        assertThat(savedTherapy.getName()).isEqualTo(therapyDto.getName());
        assertThat(savedTherapy.getDescription()).isEqualTo(therapyDto.getDescription());
        assertThat(savedTherapy.isActive()).isEqualTo(therapyDao.isActive());

        therapyDto.setId(therapyDao.getId());
        assertThat(therapyDto).isEqualTo(therapyDtoReturned);

        verify(therapyRepository).findById(therapyDtoId);
        verify(therapyRepository).save(savedTherapy);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void updateTherapyWithInvalidTherapyId_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        String therapyDtoId = UUID.randomUUID().toString();
        TherapyDto therapyDto = new TherapyDto();

        when(therapyRepository.findById(therapyDtoId)).thenReturn(Optional.empty());

        //When
        assertThatThrownBy(() -> therapyService.updateTherapy(therapyDtoId, therapyDto))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Therapy " + therapyDtoId + " not found");

        //Then
        verify(therapyRepository).findById(therapyDtoId);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void updateTherapyWithNoUpdates_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);
        ArgumentCaptor<Therapy> therapyArgumentCaptor = ArgumentCaptor.forClass(Therapy.class);

        String therapyDtoId = UUID.randomUUID().toString();
        TherapyDto therapyDto = new TherapyDto();

        Therapy therapyDao = new Therapy();
        therapyDao.setId(therapyDtoId);
        therapyDao.setName("X");
        therapyDao.setDescription("Y");
        therapyDao.setActive(true);

        when(therapyRepository.findById(therapyDtoId)).thenReturn(Optional.of(therapyDao));
        when(therapyRepository.save(therapyArgumentCaptor.capture())).thenReturn(therapyDao);

        //When
        TherapyDto therapyDtoReturned = therapyService.updateTherapy(therapyDtoId, therapyDto);

        //Then
        Therapy savedTherapy = therapyArgumentCaptor.getValue();

        assertThat(savedTherapy.getId()).isEqualTo(therapyDao.getId());
        assertThat(savedTherapy.getName()).isEqualTo(therapyDao.getName());
        assertThat(savedTherapy.getDescription()).isEqualTo(therapyDao.getDescription());
        assertThat(savedTherapy.isActive()).isEqualTo(therapyDao.isActive());

        BeanUtils.copyProperties(therapyDao, therapyDto);
        assertThat(therapyDto).isEqualTo(therapyDtoReturned);

        verify(therapyRepository).findById(therapyDtoId);
        verify(therapyRepository).save(savedTherapy);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void deactivateTherapy_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);
        ArgumentCaptor<Therapy> therapyArgumentCaptor = ArgumentCaptor.forClass(Therapy.class);

        String therapyDtoId = UUID.randomUUID().toString();

        Therapy therapyDao = new Therapy();
        therapyDao.setId(therapyDtoId);
        therapyDao.setName("X");
        therapyDao.setDescription("Y");
        therapyDao.setActive(true);

        when(therapyRepository.findById(therapyDtoId)).thenReturn(Optional.of(therapyDao));
        when(therapyRepository.save(therapyArgumentCaptor.capture())).thenReturn(therapyDao);

        //When
        therapyService.deactivateTherapy(therapyDtoId);

        //Then
        Therapy savedTherapy = therapyArgumentCaptor.getValue();

        assertThat(savedTherapy.getId()).isEqualTo(therapyDao.getId());
        assertThat(savedTherapy.getName()).isEqualTo(therapyDao.getName());
        assertThat(savedTherapy.getDescription()).isEqualTo(therapyDao.getDescription());
        assertThat(savedTherapy.isActive()).isFalse();

        verify(therapyRepository).findById(therapyDtoId);
        verify(therapyRepository).save(savedTherapy);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void deactivateTherapyWithInvalidTherapyId_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        String therapyDtoId = UUID.randomUUID().toString();

        when(therapyRepository.findById(therapyDtoId)).thenReturn(Optional.empty());

        //When
        assertThatThrownBy(() -> therapyService.deactivateTherapy(therapyDtoId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Therapy " + therapyDtoId + " not found");

        //Then
        verify(therapyRepository).findById(therapyDtoId);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void activateTherapy_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);
        ArgumentCaptor<Therapy> therapyArgumentCaptor = ArgumentCaptor.forClass(Therapy.class);

        String therapyDtoId = UUID.randomUUID().toString();

        Therapy therapyDao = new Therapy();
        therapyDao.setId(therapyDtoId);
        therapyDao.setName("X");
        therapyDao.setDescription("Y");
        therapyDao.setActive(false);

        when(therapyRepository.findById(therapyDtoId)).thenReturn(Optional.of(therapyDao));
        when(therapyRepository.save(therapyArgumentCaptor.capture())).thenReturn(therapyDao);

        //When
        therapyService.activateTherapy(therapyDtoId);

        //Then
        Therapy savedTherapy = therapyArgumentCaptor.getValue();

        assertThat(savedTherapy.getId()).isEqualTo(therapyDao.getId());
        assertThat(savedTherapy.getName()).isEqualTo(therapyDao.getName());
        assertThat(savedTherapy.getDescription()).isEqualTo(therapyDao.getDescription());
        assertThat(savedTherapy.isActive()).isTrue();

        verify(therapyRepository).findById(therapyDtoId);
        verify(therapyRepository).save(savedTherapy);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void activateTherapyWithInvalidTherapyId_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        String therapyDtoId = UUID.randomUUID().toString();

        when(therapyRepository.findById(therapyDtoId)).thenReturn(Optional.empty());

        //When
        assertThatThrownBy(() -> therapyService.activateTherapy(therapyDtoId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Therapy " + therapyDtoId + " not found");

        //Then
        verify(therapyRepository).findById(therapyDtoId);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void getTherapy_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        String therapyDtoId = UUID.randomUUID().toString();

        Therapy therapyDao = new Therapy();
        therapyDao.setId(therapyDtoId);
        therapyDao.setName("X");
        therapyDao.setDescription("Y");
        therapyDao.setActive(true);

        when(therapyRepository.findById(therapyDtoId)).thenReturn(Optional.of(therapyDao));

        //When
        TherapyDto therapyDtoReturned = therapyService.getTherapy(therapyDtoId);

        //Then
        assertThat(therapyDtoReturned.getId()).isEqualTo(therapyDtoId);

        verify(therapyRepository).findById(therapyDtoId);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void getTherapyWithInvalidTherapyId_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        String therapyDtoId = UUID.randomUUID().toString();

        when(therapyRepository.findById(therapyDtoId)).thenReturn(Optional.empty());

        //When
        assertThatThrownBy(() -> therapyService.getTherapy(therapyDtoId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Therapy " + therapyDtoId + " not found");

        //Then
        verify(therapyRepository).findById(therapyDtoId);
        verifyNoMoreInteractions(therapyRepository);
    }

    @Test
    void getTherapiesAllPageDefault_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        int pageIndex = 0;
        int pageSize = 10;
        boolean all = true;
        boolean inactive = false;

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        List<Therapy> therapiesList = new ArrayList<>();
        therapiesList.add(createTherapy("1", "A", "AA", true));
        therapiesList.add(createTherapy("2", "B", "BB", true));
        therapiesList.add(createTherapy("3", "C", "CC", false));
        therapiesList.add(createTherapy("4", "D", "DD", false));

        Page<Therapy> therapiesPage = new PageImpl<>(therapiesList, pageable, therapyRepository.count());

        when(therapyRepository.findAll(pageable)).thenReturn(therapiesPage);

        //When
        List<TherapyDto> therapyDtoListReturned = therapyService.getTherapies(pageSize, pageIndex, all, inactive);

        //Then
        assertThat(therapyDtoListReturned).containsExactly(
                createTherapyDto("1", "A", "AA"),
                createTherapyDto("2", "B", "BB"),
                createTherapyDto("3", "C", "CC"),
                createTherapyDto("4", "D", "DD")
        );
    }

    @Test
    void getTherapiesInactivePageDefault_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        int pageIndex = 0;
        int pageSize = 10;
        boolean all = false;
        boolean inactive = true;

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        List<Therapy> therapiesList = new ArrayList<>();
        therapiesList.add(createTherapy("3", "C", "CC", false));
        therapiesList.add(createTherapy("4", "D", "DD", false));

        when(therapyRepository.findAllByActive(false, pageable)).thenReturn(therapiesList);

        //When
        List<TherapyDto> therapyDtoListReturned = therapyService.getTherapies(pageSize, pageIndex, all, inactive);

        //Then
        assertThat(therapyDtoListReturned).containsExactly(
                createTherapyDto("3", "C", "CC"),
                createTherapyDto("4", "D", "DD")
        );
    }


    private static TherapyDto createTherapyDto(String id, String name, String description) {
        TherapyDto therapyDto = new TherapyDto();

        therapyDto.setId(id);

        therapyDto.setName(name);

        therapyDto.setDescription(description);

        return therapyDto;
    }

    private static Therapy createTherapy(String id, String name, String description, boolean active) {
        Therapy therapy = new Therapy();

        therapy.setId(id);

        therapy.setName(name);

        therapy.setDescription(description);

        therapy.setActive(active);

        return therapy;
    }

    @Test
    void getTherapiesActivePageDefault_Test() {
        //Given
        TherapyRepository therapyRepository = mock(TherapyRepository.class);
        TherapyServiceImpl therapyService = new TherapyServiceImpl(therapyRepository);

        int pageIndex = 0;
        int pageSize = 10;
        boolean all = false;
        boolean inactive = false;

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        List<Therapy> therapiesList = new ArrayList<>();
        therapiesList.add(createTherapy("1", "A", "AA", true));
        therapiesList.add(createTherapy("2", "B", "BB", true));

        when(therapyRepository.findAllByActive(true, pageable)).thenReturn(therapiesList);

        //When
        List<TherapyDto> therapyDtoListReturned = therapyService.getTherapies(pageSize, pageIndex, all, inactive);

        //Then
        assertThat(therapyDtoListReturned).containsExactly(
                createTherapyDto("1", "A", "AA"),
                createTherapyDto("2", "B", "BB")
        );
    }
}