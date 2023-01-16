package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.FaresDto;
import de.cinetastisch.backend.dto.OrderResponseDto;
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
    public List<OrderResponseDto> getAll(@RequestParam(value = "userId", required = false) Long userId){
        return orderService.getAllOrders(userId);
    }

    @Operation(
            tags = {"Orders"}
    )
    @GetMapping("/orders/{id}")
    public OrderResponseDto getOne(@PathVariable("id") Long id){
        return orderService.getOrder(id); //get every information and relation
    }

    @Operation(
            tags = {"Orders"}
    )
    @PutMapping("/orders/{id}/cancel")
    public OrderResponseDto cancel(@PathVariable("id") Long id){
        return orderService.cancelOrder(id);
    }

    @Operation(
            tags = {"Orders"}
    )
    @PutMapping("orders/{id}/pay")
    public OrderResponseDto pay(@PathVariable("id") Long id){
        return orderService.payOrder(id);
    }

    @Operation(
            tags = {"Orders"}
    )
    @PutMapping("orders/{id}/selectFares")
    public OrderResponseDto pay(@PathVariable("id") Long id, @RequestBody FaresDto fares){
        return orderService.selectFares(id, fares);
    }
}
