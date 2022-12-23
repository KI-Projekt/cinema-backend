package de.cinetastisch.backend.service;


import de.cinetastisch.backend.enumeration.BookingStatus;
import de.cinetastisch.backend.model.Booking;
import de.cinetastisch.backend.repository.BookingRepository;
import de.cinetastisch.backend.repository.UserRepository;
import de.cinetastisch.backend.pojo.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public List<Booking> getAllOrders(){
        return bookingRepository.findAll();
    }

    public Booking getOrderById(Long bookingId){
        return bookingRepository.findById(bookingId).get();
    }

    public List<Booking> getOrderByUserId(Long userId){
        return bookingRepository.getBookingsByUserId(userId);
    }

    public Booking createOrder(Order order){
        Booking booking = new Booking();
        booking.setUser(userRepository.findById(order.getUserId()).get());
//        order.setBookingStatus(BookingStatus.IN_PROGRESS); (ist default)
        return bookingRepository.save(booking);
    }

    public void cancelOrder(Long bookingId){
        Booking orderToCancel = bookingRepository.findById(bookingId).get();
        orderToCancel.setBookingStatus(BookingStatus.CANCELLED); // keine Löschungen
    }

    public void payOrder(Long bookingId){
        Booking paidOrder = bookingRepository.findById(bookingId).get();
        paidOrder.setBookingStatus(BookingStatus.PAID); // 💸💶💸💸💸💶💸💸💶
    }
}
