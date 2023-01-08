package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Order;
import de.cinetastisch.backend.dto.OrderRequestDto;
import de.cinetastisch.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            tags = {"Orders"}
    )
    @GetMapping("/orders")
    public List<Order> getAll(@RequestParam(value = "userId", required = false) Long userId){
        return orderService.getAllOrders(userId);
    }

    @Operation(
            tags = {"Orders"}
    )
    @GetMapping("/orders/{id}")
    public Order getOne(@PathVariable("id") Long id){
        return orderService.getOrder(id);
    }

    @Operation(
            tags = {"Orders"}
    )
    @GetMapping("/users/{userId}/orders")
    public List<Order> getAllByUserId(@PathVariable("userId") Long userId){
        return orderService.getAllOrders(userId);
    }

    @Operation(
            tags = {"Orders"}
    )
    @PutMapping("/orders/{id}/cancel")
    public Order cancel(@PathVariable("id") Long id){
        return orderService.cancelOrder(id);
    }

//    @PutMapping("orders/{id}/pay")
//    public Order pay(@PathVariable("id") Long id){
//        return orderService.payOrder(id);
//    }
}
