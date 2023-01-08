package de.cinetastisch.backend.service;


import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.OrderMapper;
import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.dto.OrderRequestDto;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final UserService userService;
    private final SeatService seatService;
    private final TicketService ticketService;
    private final ScreeningService screeningService;

    public OrderService(OrderRepository orderRepository,
                        OrderMapper mapper,
                        UserService userService,
                        SeatService seatService,
                        TicketService ticketService,
                        ScreeningService screeningService) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.seatService = seatService;
        this.ticketService = ticketService;
        this.screeningService = screeningService;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrder(Long orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("User ID not found"));
    }

    public List<Order> getOrderByUserId(Long userId){
        return orderRepository.findAllByUser(userService.getUser(userId));
    }

    public Order createOrder(OrderRequestDto newOrderRequestDto){
        Order order = mapper.dtoToEntity(newOrderRequestDto);
        Screening screening = screeningService.getScreening(newOrderRequestDto.screeningId());

//        for( Long t : newOrderRequestDto.tickets()){
//            ticketService.saveTicket(tickets());
//        }

        return orderRepository.save(order);
    }

    public void cancelOrder(Long id){
        Order orderToCancel = getOrder(id);
        orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
    }

    public void payOrder(Long bookingId){
        Order paidOrder = orderRepository.findById(bookingId).get();
        paidOrder.setOrderStatus(OrderStatus.PAID);
    }
}
