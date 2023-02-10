package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.ScreeningRequestDto;
import de.cinetastisch.backend.dto.response.ScreeningFullResponseDto;
import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.enumeration.MovieStatus;
import de.cinetastisch.backend.enumeration.ScreeningStatus;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.model.Movie;
import de.cinetastisch.backend.model.Room;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import org.springframework.data.jpa.domain.Specification;

//import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {

    @InjectMocks
    ScreeningService screeningService;

    @Mock
    ScreeningRepository screeningRepository;

    @Mock
    ScreeningMapper screeningMapper;
    final Specification<Screening> spec = null;
    final Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");


    @Test
    void getAllScreenings() {
        Screening screening = new Screening(null, null, null, null, null, true, true, null);
        List<Screening> screeningList = List.of(screening, screening);
        Page<Screening> screeningPage = new PageImpl<>(List.of(screening, screening), pageable, 0);
        ScreeningResponseDto screeningResponseDto = new ScreeningResponseDto(null, null, null, null, null, null, true, true, null);
        List<ScreeningResponseDto> screeningResponseDtoList = List.of(screeningResponseDto, screeningResponseDto);
        ScreeningFullResponseDto screeningFullResponseDto = new ScreeningFullResponseDto(null, null, null, null, null, null, null, true, true, null);
        List<ScreeningFullResponseDto> screeningFullResponseDtoList = List.of(screeningFullResponseDto, screeningFullResponseDto);
        when(screeningRepository.findAll(spec, pageable)).thenReturn(screeningPage);
        when(screeningMapper.entityToDto(screeningPage)).thenReturn(screeningFullResponseDtoList);
        when(screeningMapper.trimDto(screeningFullResponseDtoList)).thenReturn(screeningResponseDtoList);
        List<ScreeningResponseDto> response = screeningService.getAllScreenings(spec, pageable);

        assertEquals(screeningResponseDtoList, response);
    }

    @Test
    void getScreening() {
        Screening screening = new Screening(null, null, null, null, null, true, true, null);
        ScreeningFullResponseDto screeningFullResponseDto = new ScreeningFullResponseDto(null, null, null, null, null, null, null, true, true, null);
        when(screeningRepository.getReferenceById((long) 1.2)).thenReturn(screening);
        when(screeningMapper.entityToDto(screening)).thenReturn(screeningFullResponseDto);
        ScreeningFullResponseDto response = screeningService.getScreening((long) 1.2);
        assertEquals(screeningFullResponseDto, response);
    }

    @Test
    void addScreeningAlreadyOccupind() {
        LocalDateTime localDateTime = LocalDateTime.of(12, 12, 12, 12, 12, 12);
        Room room = new Room("Star Wars", true, true);
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", null, "27/10", "1222", MovieStatus.IN_CATALOG);
        Screening screening = new Screening(movie, room, localDateTime, localDateTime.plusMinutes(40), true, true, ScreeningStatus.TICKET_SALE_OPEN);
        List<Screening> screeningList = List.of(screening);
        ScreeningRequestDto screeningRequestDto = new ScreeningRequestDto((long) 1.2, (long) 1.2, localDateTime, localDateTime.plusMinutes(40), null, null, null);
        when(screeningMapper.dtoToEntity(screeningRequestDto)).thenReturn(screening);
        when(screeningRepository.findAllByRoomAndTime(room, localDateTime, localDateTime.plusMinutes(40))).thenReturn(screeningList);

        assertThrows(ResourceAlreadyOccupiedException.class, () -> screeningService.addScreening(screeningRequestDto));


    }

    @Test
    void addScreeningIllegalArgumentthreeD() {
        LocalDateTime localDateTime = LocalDateTime.of(12, 12, 12, 12, 12, 12);
        Room room = new Room("Star Wars", false, true);
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", null, "27/10", "1222", MovieStatus.IN_CATALOG);
        Screening screening = new Screening(movie, room, localDateTime, localDateTime.plusMinutes(40), true, true, ScreeningStatus.CANCELLED);
        List<Screening> screeningList = List.of(screening);
        ScreeningRequestDto screeningRequestDto = new ScreeningRequestDto((long) 1.2, (long) 1.2, localDateTime, localDateTime.plusMinutes(40), null, null, null);
        when(screeningMapper.dtoToEntity(screeningRequestDto)).thenReturn(screening);
        when(screeningRepository.findAllByRoomAndTime(room, localDateTime, localDateTime.plusMinutes(40))).thenReturn(screeningList);

        assertThrows(IllegalArgumentException.class, () -> screeningService.addScreening(screeningRequestDto));


    }

    @Test
    void addScreeningIllegalArgumentDolbyAtmos() {
        LocalDateTime localDateTime = LocalDateTime.of(12, 12, 12, 12, 12, 12);
        Room room = new Room("Star Wars", false, false);
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", null, "27/10", "1222", MovieStatus.IN_CATALOG);
        Screening screening = new Screening(movie, room, localDateTime, localDateTime.plusMinutes(40), false, true, ScreeningStatus.CANCELLED);
        List<Screening> screeningList = List.of(screening);
        ScreeningRequestDto screeningRequestDto = new ScreeningRequestDto((long) 1.2, (long) 1.2, localDateTime, localDateTime.plusMinutes(40), null, null, null);
        when(screeningMapper.dtoToEntity(screeningRequestDto)).thenReturn(screening);
        when(screeningRepository.findAllByRoomAndTime(room, localDateTime, localDateTime.plusMinutes(40))).thenReturn(screeningList);

        assertThrows(IllegalArgumentException.class, () -> screeningService.addScreening(screeningRequestDto));


    }


    @Test
    void addScreening() {
        ScreeningFullResponseDto screeningFullResponseDto = new ScreeningFullResponseDto(null, null, null, null, null, null, null, true, true,null);
        LocalDateTime localDateTime = LocalDateTime.of(12, 12, 12, 12, 12, 12);
        Room room = new Room("Star Wars", false, false);
        Movie movie = new Movie("Test", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", null, "27/10", "1222", MovieStatus.IN_CATALOG);
        Screening screening = new Screening(movie, room, localDateTime, localDateTime.plusMinutes(40), false, false, ScreeningStatus.CANCELLED);
        List<Screening> screeningList = List.of(screening);
        ScreeningRequestDto screeningRequestDto = new ScreeningRequestDto((long) 1.2, (long) 1.2, localDateTime, localDateTime.plusMinutes(40), null, null, null);
        when(screeningMapper.dtoToEntity(screeningRequestDto)).thenReturn(screening);
        when(screeningRepository.findAllByRoomAndTime(room, localDateTime, localDateTime.plusMinutes(40))).thenReturn(screeningList);
        when(screeningRepository.save(screening)).thenReturn(screening);
        when(screeningMapper.entityToDto(screening)).thenReturn(screeningFullResponseDto);

        ScreeningFullResponseDto response = screeningService.addScreening(screeningRequestDto);

        assertEquals(screeningFullResponseDto,response);
    }

    @Test
    void replaceScreening() {
        Screening screening = new Screening(null, null, null, null, null, true, true, null);
        ScreeningRequestDto screeningRequestDto = new ScreeningRequestDto(null, null, null, null, null, null, null);
        ScreeningFullResponseDto screeningFullResponseDto = new ScreeningFullResponseDto(null, null, null, null, null, null, null, true, true, null);
        when(screeningRepository.getReferenceById((long) 1.2)).thenReturn(screening);
        when(screeningMapper.dtoToEntity(screeningRequestDto)).thenReturn(screening);
        when(screeningRepository.save(screening)).thenReturn(screening);
        when(screeningMapper.entityToDto(screening)).thenReturn(screeningFullResponseDto);
        ScreeningFullResponseDto response = screeningService.replaceScreening((long) 1.2, screeningRequestDto);

        assertEquals(screeningFullResponseDto, response);
    }

    @Test
    void deleteScreening() {
        Screening screening = new Screening(null, null, null, null, null, true, true, null);
        screeningService.deleteScreening((long) 1.2);
    }

    @Test
    void calculateEndDateTime() {
        Movie movie = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "10", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222", MovieStatus.IN_CATALOG);
        LocalDateTime localDateTime = LocalDateTime.of(12, 12, 12, 12, 10, 12);
        LocalDateTime localDateTimePlus = LocalDateTime.of(12, 12, 12, 12, 50, 12);
        LocalDateTime response = screeningService.calculateEndDateTime(localDateTime, movie);

        assertEquals(localDateTimePlus, response);
    }

    @Test
    void cancelScreening() {
        Screening screening = new Screening(null, null, null, null, null, true, true, null);
        ScreeningFullResponseDto screeningFullResponseDto = new ScreeningFullResponseDto(null, null, null, null, null, null, null, true, true, null);
        when(screeningRepository.getReferenceById((long) 1.2)).thenReturn(screening);
        when(screeningRepository.save(screening)).thenReturn(screening);
        when(screeningMapper.entityToDto(screening)).thenReturn(screeningFullResponseDto);
        ScreeningFullResponseDto response = screeningService.cancelScreening((long) 1.2);

        assertEquals(screeningFullResponseDto, response);
    }

    @Test
    void cancelScreeningAlready() {
        Screening screening = new Screening(null, null, null, null, null, true, true, ScreeningStatus.CANCELLED);
        when(screeningRepository.getReferenceById((long) 1.2)).thenReturn(screening);
        assertThrows(ResourceAlreadyExistsException.class, () -> screeningService.cancelScreening((long) 1.2));
    }
}