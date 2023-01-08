package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.ReservationRequestDto;
import de.cinetastisch.backend.exception.ResourceAlreadyOccupiedException;
import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.model.Reservation;
import de.cinetastisch.backend.model.Screening;
import de.cinetastisch.backend.model.Seat;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.ReservationRepository;
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
    private final UserService userService;
    private final SeatService seatService;
    private final ScreeningService screeningService;

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void deleteExpiredReservations(){
        reservationRepository.deleteByExpireAtIsLessThanEqual(LocalDateTime.now());
    }

    public void deleteReservation(Long id){
        reservationRepository.delete(getReservation(id));
    }

    public List<Reservation> getAllReservations(Long screeningId){
        if(screeningId != null){
            return reservationRepository.findAllByScreening(screeningService.getScreening(screeningId));
        }
        return reservationRepository.findAll();
    }

    public Reservation getReservation(Long id){
        return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }

    public Reservation getReservation(User user, Screening screening, Seat seat){
        return reservationRepository.findByUserAndScreeningAndSeat(user, screening, seat).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }

    public Reservation addReservation(ReservationRequestDto request){
        Reservation reservation = Reservation.builder()
                                             .user(userService.getUser(request.userId()))
                                             .screening(screeningService.getScreening(request.screeningId()))
                                             .seat(seatService.getSeat(request.seatId()))
                                             .build();
        deleteExpiredReservations();
        if(reservationRepository.existsByScreeningAndSeat(reservation.getScreening(), reservation.getSeat())){
            throw new ResourceAlreadyOccupiedException("Seat is currently reserved");
        }

        return reservationRepository.save(reservation);
    }
}
