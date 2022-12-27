package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAll(){
        return orderService.getAllOrders();
    }

    @GetMapping("orders/{id}")
    public Order getOne(@PathVariable("id") Long id){
        return orderService.getOrderById(id);
    }

    @GetMapping("/users/orders/{userId}")
    public List<Order> getAllByUserId(@PathVariable("userId") Long userId){
        return orderService.getOrderByUserId(userId);
    }

    @PostMapping("/orders")
    public Order placeOrder(@RequestBody de.cinetastisch.backend.pojo.Order order){
        return orderService.createOrder(order);
    }
}
