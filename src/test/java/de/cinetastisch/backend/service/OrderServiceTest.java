package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.repository.TicketFareRepository;
import de.cinetastisch.backend.repository.TicketRepository;
import de.cinetastisch.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderMapper orderMapper;

    @Mock
    TicketFareRepository ticketFareRepository;

    @Mock
    TicketRepository ticketRepository;

    @Mock
    UserRepository userRepository;

    final Specification<Screening> spec = null;
    final Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");

    @Test
    void getAllOrders() {
    }

    @Test
    void getOrder() {
        Order order = new Order(null, null);
        OrderResponseDto orderResponseDto = new OrderResponseDto(null, null, null, null, null, null, null, null, null, null);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        when(orderMapper.entityToDto(order)).thenReturn(orderResponseDto);

        OrderResponseDto response = orderService.getOrder((long)1.2);

        assertEquals(orderResponseDto, response);
    }

    @Test
    void selectFares() {
    }

    @Test
    void selectPaymentMethod() {
    }

    @Test
    void payOrder() {
    }

    @Test
    void cancelOrder() {
    }

    @Test
    void transferOrderToUser() {
    }

    @Test
    void refundOrder() {
    }
}