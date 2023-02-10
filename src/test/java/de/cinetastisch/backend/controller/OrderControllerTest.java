package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;


    @Test
    void getAll() {
        OrderResponseDto responseDto = new OrderResponseDto(null, null, null, null, null, null, null, null, null, null);
        List<OrderResponseDto> responseDtoList = List.of(responseDto, responseDto);

        when(orderService.getAllOrders(null, null)).thenReturn(responseDtoList);

        ResponseEntity<?> response = orderController.getAll(null, null);

        assertEquals(responseDtoList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getOne() {
        OrderResponseDto responseDto = new OrderResponseDto(null, null, null, null, null, null, null, null, null, null);
        when(orderService.getOrder((long) 1.2)).thenReturn(responseDto);
        ResponseEntity<?> response = orderController.getOne((long) 1.2);
        assertEquals(responseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void cancel() {
        OrderResponseDto responseDto = new OrderResponseDto(null, null, null, null, null, null, null, null, null, null);
        when(orderService.cancelOrder((long) 1.2)).thenReturn(responseDto);
        ResponseEntity<?> response = orderController.cancel((long) 1.2);

        assertEquals(responseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void refund() {
        OrderResponseDto responseDto = new OrderResponseDto(null, null, null, null, null, null, null, null, null, null);
        when(orderService.refundOrder((long) 1.2)).thenReturn(responseDto);
        ResponseEntity<?> response = orderController.refund((long) 1.2);
        assertEquals(responseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void pay() {
        OrderResponseDto responseDto = new OrderResponseDto(null, null, null, null, null, null, null, null, null, null);

        when(orderService.payOrder((long) 1.2)).thenReturn(responseDto);
        ResponseEntity<?> response = orderController.pay((long) 1.2);
        assertEquals(responseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void selectFares() {
        OrderResponseDto responseDto = new OrderResponseDto(null, null, null, null, null, null, null, null, null, null);

        when(orderService.selectFares((long) 1.2, null)).thenReturn(responseDto);
        ResponseEntity<?> response = orderController.selectFares((long) 1.2, null);

        assertEquals(responseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void selectPaymentMethod() {
        OrderResponseDto responseDto = new OrderResponseDto(null, null, null, null, null, null, null, null, null, null);
        when(orderService.selectPaymentMethod((long) 1.2, "test")).thenReturn(responseDto);
        ResponseEntity<?> response = orderController.selectPaymentMethod((long) 1.2, "test");
        assertEquals(responseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void convertSessionToUser() {
        OrderResponseDto responseDto = new OrderResponseDto(null, null, null, null, null, null, null, null, null, null);
        when(orderService.transferOrderToUser((long) 1.2, (long) 1.2)).thenReturn(responseDto);
        ResponseEntity<?> response = orderController.convertSessionToUser((long) 1.2, (long) 1.2);
        assertEquals(responseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}