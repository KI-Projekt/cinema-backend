package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.dto.response.UserResponseDto;
import de.cinetastisch.backend.enumeration.OrderPaymentMethod;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @InjectMocks
    OrderController orderController;
    @Mock
    OrderService orderService;

    @Test
    void getAll() {
        UserResponseDto userResponseDto = new UserResponseDto((long)1.222,"Anthon", "Maier","anthon.maier@test.de");
        OrderResponseDto firstOrderResponseDto = new OrderResponseDto((long)1.222, userResponseDto, "session1", OrderStatus.IN_PROGRESS, 1222.0, OrderPaymentMethod.CASH, LocalDateTime.now(), LocalDateTime.now(), null,null);
        OrderResponseDto secondOrderResponseDto = new OrderResponseDto((long)1.222,userResponseDto, "session1", OrderStatus.IN_PROGRESS, 1222.0, OrderPaymentMethod.CASH, LocalDateTime.now(),  LocalDateTime.now(),null,null);
        List<OrderResponseDto> expected = List.of(firstOrderResponseDto, secondOrderResponseDto);

        when(orderService.getAllOrders((long)1.2222)).thenReturn(expected);

        ResponseEntity<List<OrderResponseDto>> response = orderController.getAll((long)1.2222);
        assertAll(
                () -> assertEquals(response.getBody(), expected)
        );
    }



//    @Test
//    void getOne() {
//        User user = new User("Peter", "Schmitt", "p.s@mail.de", "password", "31.12.2000", "Deutschland", "Mannheim", "68245", "Strasse", 4);
//        UserResponseDto userResponseDto = new UserResponseDto((long)1.222,"Anthon", "Maier","anthon.maier@test.de");
//        OrderResponseDto firstOrderResponseDto = new OrderResponseDto((long)1.222,userResponseDto, OrderStatus.IN_PROGRESS,1222);
//
//        when(orderController.getOne((long)1.22)).thenReturn(firstOrderResponseDto);
//
//        OrderResponseDto responseDto = orderController.getOne((long)1.22);
//        assertEquals(firstOrderResponseDto,responseDto);
//    }

//    @Test
//    void getAllByUserId() {
//        User user = new User("Peter", "Schmitt", "p.s@mail.de", "password", "31.12.2000", "Deutschland", "Mannheim", "68245", "Strasse", 4);
//        UserResponseDto userResponseDto = new UserResponseDto((long)1.222,"Anthon", "Maier","anthon.maier@test.de");
//        OrderResponseDto firstOrderResponseDto = new OrderResponseDto((long)1.222,userResponseDto, OrderStatus.IN_PROGRESS,1222);
//        OrderResponseDto secondOrderResponseDto = new OrderResponseDto((long)1.222,userResponseDto, OrderStatus.IN_PROGRESS,1222);
//        List<OrderResponseDto> expected = List.of(firstOrderResponseDto, secondOrderResponseDto);
//
//        when(orderService.getAllOrders((long)1.2222)).thenReturn(expected);
//
//        List<?> response = orderController.getAllByUserId((long)1.2222);
//        assertAll(
//                () -> assertEquals(response, expected)
//        );
//    }

//    @Test
//    void cancel() {
//        User user = new User("Peter", "Schmitt", "p.s@mail.de", "password", "31.12.2000", "Deutschland", "Mannheim", "68245", "Strasse", 4);
//        UserResponseDto userResponseDto = new UserResponseDto((long)1.222,"Anthon", "Maier","anthon.maier@test.de");
//        OrderResponseDto firstOrderResponseDto = new OrderResponseDto((long)1.222,userResponseDto, OrderStatus.IN_PROGRESS,1222);
//
//        when(orderService.cancelOrder((long)1.2222)).thenReturn(firstOrderResponseDto);
//
//        OrderResponseDto response = orderController.cancel((long)1.2222);
//        assertAll(
//                () -> assertEquals(response, firstOrderResponseDto)
//        );
//    }

    }
