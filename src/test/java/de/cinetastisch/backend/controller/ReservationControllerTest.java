package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.ReservationRequestDto;
import de.cinetastisch.backend.dto.response.TicketResponseDto;
import de.cinetastisch.backend.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
    @InjectMocks
    ReservationController reservationController;
    @Mock
    ReservationService reservationService;

    @Test
    void getAll() {
        TicketResponseDto responseDto = new TicketResponseDto(null,null,null,null,null,null,null);
        List<TicketResponseDto> responseDtoList = List.of(responseDto,responseDto);
        when(reservationService.getAllReservations((long)1.2,(long)1.2)).thenReturn(responseDtoList);

        ResponseEntity<?> respone = reservationController.getAll((long)1.2,(long)1.2);

        assertEquals(responseDtoList,respone.getBody());
        assertEquals(HttpStatus.OK,respone.getStatusCode());


    }

    @Test
    void addReservation() throws Exception {
        HttpServletRequest curRequest = mock(HttpServletRequest.class);

        ReservationRequestDto requestDto = new ReservationRequestDto((long)1.22, (long)1.22, (long)1.22);
        ResponseEntity<?> response = reservationController.addReservation(requestDto, curRequest);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());

    }
    @Test
    void cancelReservation() {
         ResponseEntity<?> respone = reservationController.cancelReservation((long)1.2);

        assertEquals(HttpStatus.NO_CONTENT, respone.getStatusCode());


    }
}