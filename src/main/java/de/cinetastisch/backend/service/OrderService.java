package de.cinetastisch.backend.service;


import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long bookingId){
        return orderRepository.findById(bookingId).get();
    }

    public List<Order> getOrderByUserId(Long userId){
        return orderRepository.getOrdersByUserId(userId);
    }

    public Order createOrder(de.cinetastisch.backend.pojo.Order newOrder){
        Order order = new Order(userRepository.findById(newOrder.getUserId()).get());
//        order.setOrderStatus(OrderStatus.IN_PROGRESS); (ist default)
        return orderRepository.save(order);
    }

    public void cancelOrder(Long bookingId){
        Order orderToCancel = orderRepository.findById(bookingId).get();
        orderToCancel.setOrderStatus(OrderStatus.CANCELLED); // keine Löschungen
    }

    public void payOrder(Long bookingId){
        Order paidOrder = orderRepository.findById(bookingId).get();
        paidOrder.setOrderStatus(OrderStatus.PAID); // 💸💶💸💸💸💶💸💸💶
    }
}
