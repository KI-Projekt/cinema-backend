package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.ReservationRequestDto;
import de.cinetastisch.backend.dto.ReservationResponseDto;
import de.cinetastisch.backend.enumeration.OrderStatus;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.mapper.ReferenceMapper;
import de.cinetastisch.backend.mapper.ReservationMapper;
import de.cinetastisch.backend.mapper.ScreeningMapper;
import de.cinetastisch.backend.mapper.UserMapper;
import de.cinetastisch.backend.model.*;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.repository.ReservationRepository;
import de.cinetastisch.backend.repository.SeatRepository;
import de.cinetastisch.backend.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final ReservationMapper mapper;
    private final ReferenceMapper referenceMapper;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void deleteExpiredReservations(){
        if(reservationRepository.existsByExpiresAtIsLessThanEqual(LocalDateTime.now())){
            List<Order> ordersOfReservations = reservationRepository.getAllDistinctOrdersOfReservationsToDelete(LocalDateTime.now());

            for (Order o : ordersOfReservations){
                if(o.getOrderStatus() != OrderStatus.PAID){
                    System.out.println(o);
                    o.setOrderStatus(OrderStatus.CANCELLED);
                    orderRepository.save(o);
                }
                reservationRepository.deleteAllByOrder(o);
            }
//            reservationRepository.deleteByExpireAtIsLessThanEqual(LocalDateTime.now());

//            orderService.cancelOrder();
        }
    }

    public void deleteReservation(Long id){
        reservationRepository.delete(getReservation(id));
    }

    public List<ReservationResponseDto> getAllReservations(Long userId, Long screeningId){
        if (userId != null && screeningId != null){
            return mapper.entityToDto(reservationRepository.findAllByUserAndScreening(referenceMapper.map(userId, User.class), referenceMapper.map(screeningId, Screening.class)));
        } else if (userId != null){
            return mapper.entityToDto(reservationRepository.findAllByUser(referenceMapper.map(userId, User.class)));
        } else if(screeningId != null){
            return mapper.entityToDto(reservationRepository.findAllByScreening(referenceMapper.map(screeningId, Screening.class)));
        }
        return mapper.entityToDto(reservationRepository.findAll());
    }

    public Reservation getReservation(Long id){
        return reservationRepository.getReferenceById(id);
    }

    public Reservation getReservation(User user, Screening screening, Seat seat){
        return reservationRepository.findByUserAndScreeningAndSeat(user, screening, seat).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }

    @Transactional
    public ReservationResponseDto addReservation(ReservationRequestDto request){
        deleteExpiredReservations();
        Reservation reservation = mapper.dtoToEntity(request);
        // Check for reservations
        if(reservation.getSeat().getRoom() != reservation.getScreening().getRoom()){
            throw new IllegalArgumentException("Seat ID not in Screening");
        }


        if((reservationRepository.existsByScreeningAndSeatAndExpiresAtIsGreaterThanEqual(reservation.getScreening(), reservation.getSeat(), LocalDateTime.now()))
                || ticketRepository.existsByScreeningAndSeat(reservation.getScreening(), reservation.getSeat())){
            throw new ResourceAlreadyOccupiedException("Seat is currently reserved");
        }

        if(!reservationRepository.existsByUserAndScreening(reservation.getUser(), reservation.getScreening())){
            Order order = orderRepository.save(new Order(reservation.getUser()));
            reservation.setOrder(order);
        } else {
            Reservation firstReservation = reservationRepository.findAllByUserAndScreening(reservation.getUser(), reservation.getScreening()).get(0);
            reservation.setOrder(firstReservation.getOrder());
            reservation.setExpiresAt(firstReservation.getExpiresAt());
        }
        System.out.println(reservation);
        return mapper.entityToDto(reservationRepository.save(reservation));
    }

    public List<Reservation> getAllReservations(Order order) {
        return reservationRepository.findAllByOrder(order);
    }
}
