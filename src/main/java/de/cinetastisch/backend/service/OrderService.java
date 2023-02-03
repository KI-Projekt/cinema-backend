package de.cinetastisch.backend.service;


import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.enumeration.OrderPaymentMethod;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.enumeration.TicketType;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.mapper.ReferenceMapper;
import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.model.Ticket;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.repository.TicketFareRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ReferenceMapper referenceMapper;
    private final TicketFareRepository ticketFareRepository;


    public List<OrderResponseDto> getAllOrders(Long userId){
        if(userId != null){
            User user = referenceMapper.map(userId, User.class);
            return orderMapper.entityToDto(orderRepository.findAllByUser(user));
        }
        return orderMapper.entityToDto(orderRepository.findAll());
    }

    public OrderResponseDto getOrder(Long id){
        Order order = orderRepository.getReferenceById(id);
        return orderMapper.entityToDto(order);
    }

    @Transactional
    public OrderResponseDto selectFares(Long id, Map<String, Integer> fares) {
        Order order = orderRepository.getReferenceById(id);

        if(order.getStatus() != OrderStatus.IN_PROGRESS){
            throw new ResourceAlreadyExistsException("Order is already " + order.getStatus());
        }

        Integer sumOfFares = 0;

        for(Map.Entry<String,Integer> f : new ArrayList<>(fares.entrySet())){
            sumOfFares += f.getValue();
        }

        if(order.getTickets().size() != sumOfFares){
            throw new IllegalArgumentException("Number of fares does not match with the number of reservations");
        }

        for(Map.Entry<String, Integer> f : new ArrayList<>(fares.entrySet())){
            for(int i = 0; i < f.getValue(); i++){
                order.getTickets().get(i).setSelectedFare(ticketFareRepository.findByNameLikeIgnoreCase(f.getKey()));
            }
        }

        orderRepository.save(order);
        return orderMapper.entityToDto(order);
    }

    public OrderResponseDto selectPaymentMethod(Long id, String method){
        Order order = orderRepository.getReferenceById(id);

        if(order.getStatus() != OrderStatus.IN_PROGRESS){
            throw new IllegalArgumentException("Order cannot be paid because it's " + order.getStatus());
        }

        OrderPaymentMethod newMethod = OrderPaymentMethod.valueOf(method);
        OrderPaymentMethod oldMethod = order.getPaymentMethod();

        if(newMethod == oldMethod){
            throw new ResourceAlreadyExistsException("Payment method of order " + id + " is already set to " + oldMethod);
        }
        order.setPaymentMethod(newMethod);

        if(newMethod == OrderPaymentMethod.CASH){
            order.setExpiresAt(order.getCreatedAt().plusMinutes(20));
        } else {
            order.setExpiresAt(order.getCreatedAt().plusMinutes(1));
        }
        orderRepository.save(order);
        return orderMapper.entityToDto(order);
    }

    @Transactional
    public OrderResponseDto payOrder(Long id){
        Order paidOrder = orderRepository.getReferenceById(id);

        if(paidOrder.getStatus() != OrderStatus.IN_PROGRESS){
            throw new IllegalArgumentException("Order cannot be paid because it's " + paidOrder.getStatus());
        }

        for(Ticket t : paidOrder.getTickets()) {
            t.setType(TicketType.TICKET);
        }

        paidOrder.setStatus(OrderStatus.PAID);
        orderRepository.save(paidOrder);
        return orderMapper.entityToDto(paidOrder);
    }

    @Transactional
    public OrderResponseDto cancelOrder(Long id){
        Order orderToCancel = orderRepository.getReferenceById(id);

        if(orderToCancel.getStatus() != OrderStatus.IN_PROGRESS){
            throw new ResourceAlreadyExistsException("Order is already " + orderToCancel.getStatus());
        }

        orderToCancel.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(orderToCancel);
        return orderMapper.entityToDto(orderToCancel);
    }

}
