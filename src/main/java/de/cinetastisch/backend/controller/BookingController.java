package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.Booking;
import de.cinetastisch.backend.model.Ticket;
import de.cinetastisch.backend.service.BookingService;
import de.cinetastisch.backend.pojo.Order;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping()
    public List<Booking> getAll(){
        return bookingService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Booking getOne(@PathVariable("id") Long id){
        return bookingService.getOrderById(id);
    }

    @GetMapping("{userId}")
    public List<Booking> getAllByUserId(@PathVariable("userId") Long userId){
        return bookingService.getOrderByUserId(userId);
    }

    @PostMapping
    public Booking placeOrder(@RequestBody Order order){
        return bookingService.createOrder(order);
    };

//        Ticket = new Ticket();
}
