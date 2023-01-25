package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.*;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.SeatCategory;
import de.cinetastisch.backend.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
    @InjectMocks
    ReservationController reservationController;
    @Mock
    ReservationService reservationService;

    @Test
    void getAll() {
        LocalDateTime localDateTime = LocalDateTime.of(2022,12,12,12,12,12);
        SeatResponseDto seatResponseDto =new SeatResponseDto((long)1.22, SeatCategory.PREMIUM,2,2);
        UserResponseDto userResponseDto = new UserResponseDto((long)1.222,"Anthon", "Maier","anthon.maier@test.de");
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.222,userResponseDto, OrderStatus.IN_PROGRESS,1222);

        ReservationResponseDto firstresponseDto = new ReservationResponseDto((long)1.22,(long)1.22,orderResponseDto,seatResponseDto,localDateTime);
        ReservationResponseDto secondresponseDto = new ReservationResponseDto((long)1.22,(long)1.22,orderResponseDto,seatResponseDto,localDateTime);
        List<ReservationResponseDto> expected = List.of(firstresponseDto,secondresponseDto);

        when(reservationService.getAllReservations((long)1.22,(long)1.22)).thenReturn(expected);

        List<ReservationResponseDto> response = reservationController.getAll((long)1.22,(long)1.22);
        assertEquals(expected, response);



    }

    @Test
    void addReservation() throws Exception {

        ReservationRequestDto requestDto = new ReservationRequestDto((long)1.22,(long)1.22,(long)1.22);
        ResponseEntity<?> response = reservationController.addReservation(requestDto);

        assertEquals(response.getStatusCode(),HttpStatus.CREATED);

    }
}