package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.enumeration.OrderPaymentMethod;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.Ticket;
import de.cinetastisch.backend.model.User;
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

import java.time.LocalDate;
import java.util.List;

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
    void selectPaymentMethodException() {
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.CANCELLED);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        assertThrows(IllegalArgumentException.class, ()->orderService.selectPaymentMethod(((long)1.2), "Ha"));
    }

    @Test
    void selectPaymentMethod(){
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.IN_PROGRESS);
        OrderPaymentMethod orderPaymentMethod = OrderPaymentMethod.PAYPAL;
        order.setPaymentMethod(orderPaymentMethod);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        assertThrows(ResourceAlreadyExistsException.class, ()->orderService.selectPaymentMethod((long)1.2,"PAYPAL"));

    }

    @Test
    void selectPaymentMethodNewMethod(){
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.IN_PROGRESS);
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.2, null, null, OrderStatus.CANCELLED, null, null, null, null, null, null);
        OrderPaymentMethod orderPaymentMethod = OrderPaymentMethod.PAYPAL;
        order.setPaymentMethod(orderPaymentMethod);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.entityToDto(order)).thenReturn(orderResponseDto);
        OrderResponseDto response = orderService.selectPaymentMethod((long)1.2, "CASH");
        assertEquals(orderResponseDto, response);

    }

    @Test
    void selectPaymentMethodExpires(){
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.IN_PROGRESS);
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.2, null, null, OrderStatus.CANCELLED, null, null, null, null, null, null);
        OrderPaymentMethod orderPaymentMethod = OrderPaymentMethod.PAYPAL;
        order.setPaymentMethod(orderPaymentMethod);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.entityToDto(order)).thenReturn(orderResponseDto);
        OrderResponseDto response = orderService.selectPaymentMethod((long)1.2, "GIROPAY");
        assertEquals(orderResponseDto, response);

    }
    @Test
    void payOrder() {
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.CANCELLED);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        assertThrows(IllegalArgumentException.class, ()->orderService.payOrder((long)1.2));
    }

    @Test
    void payOrderInProgress(){
        Order order = new Order(null, null);
        Ticket ticket = new Ticket(null, null, null, null, null);
        List<Ticket> ticketList = List.of(ticket);
        order.setTickets(ticketList);
        order.setStatus(OrderStatus.IN_PROGRESS);
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.2, null, null, OrderStatus.CANCELLED, null, null, null, null, null, null);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.entityToDto(order)).thenReturn(orderResponseDto);
        OrderResponseDto response = orderService.payOrder((long)1.2);

        assertEquals(orderResponseDto, response);
    }

    @Test
    void cancelOrderException() {
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.CANCELLED);
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.2, null, null, OrderStatus.CANCELLED, null, null, null, null, null, null);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        assertThrows(ResourceAlreadyExistsException.class, ()->orderService.cancelOrder((long)1.2));
    }

    @Test
    void cancelOrder(){
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.IN_PROGRESS);
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.2, null, null, OrderStatus.CANCELLED, null, null, null, null, null, null);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.entityToDto(order)).thenReturn(orderResponseDto);
        OrderResponseDto response = orderService.cancelOrder((long)1.2);

        assertEquals(orderResponseDto, response);
    }

    @Test
    void transferOrderToUser() {
        User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);
        Order order = new Order(user, null);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        when(userRepository.getReferenceById((long)1.2)).thenReturn(user);
        assertThrows(ResourceAlreadyExistsException.class, ()->orderService.transferOrderToUser(((long)1.2), (long)1.));

    }

    @Test
    void transferOrderToUserNullUser(){
        User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);
        Order order = new Order(null, null);
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.2, null, null, OrderStatus.CANCELLED, null, null, null, null, null, null);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        when(userRepository.getReferenceById((long)1.2)).thenReturn(user);
        when(orderMapper.entityToDto(order)).thenReturn(orderResponseDto);
        OrderResponseDto response = orderService.transferOrderToUser(((long)1.2), (long)1.);

        assertEquals(orderResponseDto, response);
    }

    @Test
    void refundOrderRefunded() {
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.REFUNDED);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        assertThrows(ResourceAlreadyExistsException.class, ()->orderService.refundOrder((long)1.2));
    }

    @Test
    void refundOrderPaid(){
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.IN_PROGRESS);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        assertThrows(ResourceAlreadyExistsException.class, ()->orderService.refundOrder((long)1.2));

    }

    @Test
    void refundOrder(){
        Order order = new Order(null, null);
        order.setStatus(OrderStatus.PAID);
        OrderResponseDto orderResponseDto = new OrderResponseDto((long)1.2, null, null, OrderStatus.CANCELLED, null, null, null, null, null, null);
        when(orderRepository.getReferenceById((long)1.2)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.entityToDto(order)).thenReturn(orderResponseDto);
        OrderResponseDto response = orderService.refundOrder((long)1.2);

        assertEquals(orderResponseDto, response);
    }
}