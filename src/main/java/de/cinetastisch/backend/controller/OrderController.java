package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.dto.OrderRequestDto;
import de.cinetastisch.backend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getAll(){
        return orderService.getAllOrders();
    }

    @GetMapping("orders/{id}")
    public Order getOne(@PathVariable("id") Long id){
        return orderService.getOrder(id);
    }

    @GetMapping("/users/orders/{userId}")
    public List<Order> getAllByUserId(@PathVariable("userId") Long userId){
        return orderService.getOrderByUserId(userId);
    }

    @PostMapping("/orders")
    public Order placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.createOrder(orderRequestDto);
    }
}
