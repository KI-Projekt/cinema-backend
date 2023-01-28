package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.ReservationRequestDto;
import de.cinetastisch.backend.service.ReservationService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
    @InjectMocks
    ReservationController reservationController;
    @Mock
    ReservationService reservationService;

//    @Test
//    void getAll() {
//        LocalDateTime localDateTime = LocalDateTime.of(2022,12,12,12,12,12);
//        SeatResponseDto seatResponseDto =new SeatResponseDto((long)1.22, SeatCategory.PREMIUM,2,2);
//        UserResponseDto userResponseDto = new UserResponseDto((long)1.222,"Anthon", "Maier","anthon.maier@test.de");
//        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.222,userResponseDto, OrderStatus.IN_PROGRESS,1222);
//
//        ReservationResponseDto firstresponseDto = new ReservationResponseDto((long)1.22,(long)1.22,orderResponseDto,seatResponseDto,localDateTime);
//        ReservationResponseDto secondresponseDto = new ReservationResponseDto((long)1.22,(long)1.22,orderResponseDto,seatResponseDto,localDateTime);
//        List<ReservationResponseDto> expected = List.of(firstresponseDto,secondresponseDto);
//
//        when(reservationService.getAllReservations((long)1.22,(long)1.22)).thenReturn(expected);
//
//        List<ReservationResponseDto> response = reservationController.getAll((long)1.22,(long)1.22);
//        assertEquals(expected, response);
//
//
//
//    }

    @Test
    void addReservation() throws Exception {
        HttpSession session = new HttpSession() {
            @Override
            public long getCreationTime() {
                return 0;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public long getLastAccessedTime() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public void setMaxInactiveInterval(int interval) {

            }

            @Override
            public int getMaxInactiveInterval() {
                return 0;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public void removeAttribute(String name) {

            }

            @Override
            public void invalidate() {

            }

            @Override
            public boolean isNew() {
                return false;
            }
        };
        ReservationRequestDto requestDto = new ReservationRequestDto((long)1.22, (long)1.22, (long)1.22);
        ResponseEntity<?> response = reservationController.addReservation(requestDto, session);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());

    }
}