package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.response.OrderResponseDto;
import de.cinetastisch.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<OrderResponseDto>> getAll(@RequestParam(value = "userId", required = false) Long userId){
        return ResponseEntity.ok(orderService.getAllOrders(userId));
    }

    @Operation(
            tags = {"Orders"}
    )
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDto> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.getOrder(id)); //get every information and relation
    }

    @Operation(
            tags = {"Orders"}
    )
    @PutMapping("/orders/{id}/cancel")
    public ResponseEntity<OrderResponseDto> cancel(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @Operation(
            tags = {"Orders"}
    )
    @PutMapping("orders/{id}/pay")
    public ResponseEntity<OrderResponseDto> pay(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.payOrder(id));
    }

    @Operation(
            tags = {"Orders"}
    )
    @PutMapping("orders/{id}/selectFares")
    public ResponseEntity<OrderResponseDto> selectFares(@PathVariable("id") Long id, @RequestBody Map<String, Integer> fareSelection){
        return ResponseEntity.ok(orderService.selectFares(id, fareSelection));
    }

    @Operation(
            tags = {"Orders"}
    )
    @PutMapping("orders/{id}/selectPaymentMethod")
    public ResponseEntity<OrderResponseDto> selectPaymentMethod(@PathVariable("id") Long id, @RequestParam(value = "method") String paymentMethod){
        return ResponseEntity.ok(orderService.selectPaymentMethod(id, paymentMethod));
    }
}
