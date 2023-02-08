package de.cinetastisch.backend;

import de.cinetastisch.backend.model.OpeningHour;
import de.cinetastisch.backend.model.TicketFare;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.OpeningHourRepository;
import de.cinetastisch.backend.repository.TicketFareRepository;
import de.cinetastisch.backend.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private TicketFareRepository ticketFareRepository;
    private OpeningHourRepository openingHourRepository;

    public DataLoader(UserRepository userRepository, PasswordEncoder encoder, TicketFareRepository ticketFareRepository,
                      OpeningHourRepository openingHourRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.ticketFareRepository = ticketFareRepository;
        this.openingHourRepository = openingHourRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(ticketFareRepository.findAll().size() == 0 ){
            TicketFare fare1 = new TicketFare("Adult", 10.0, "Über 18");
            ticketFareRepository.save(fare1);
        }



        if(userRepository.findAll().size() == 0){
            userRepository.save(new User("Max", "Mustermann", "max@mustermann.de",
                                         encoder.encode("password"),
                                         LocalDate.of(2002,2,3), "Deutschland", "Mannheim", "12332",
                                         "gangster street", 123));
            User admin = new User("Frank", "Admin", "admin@admin.de",
                                         encoder.encode("password"),
                                        LocalDate.of(1900,2,3), "Deutschland", "Mannheim", "12332",
                                         "gangster street", 123);
            admin.setRole("ROLE_USER,ROLE_ADMIN");
            userRepository.save(admin);
        }

        if(openingHourRepository.findAll().size() == 0){
            OpeningHour monday = new OpeningHour(DayOfWeek.MONDAY,          LocalTime.of(12,0), LocalTime.of(20,0));
            OpeningHour tuesday = new OpeningHour(DayOfWeek.TUESDAY,        LocalTime.of(12,0), LocalTime.of(20,0));
            OpeningHour wednesday = new OpeningHour(DayOfWeek.WEDNESDAY,    LocalTime.of(12,0), LocalTime.of(20,0));
            OpeningHour thursday = new OpeningHour(DayOfWeek.THURSDAY,      LocalTime.of(12,0), LocalTime.of(20,0));
            OpeningHour friday = new OpeningHour(DayOfWeek.FRIDAY,          LocalTime.of(12,0), LocalTime.of(20,0));
            OpeningHour saturday = new OpeningHour(DayOfWeek.SATURDAY,      LocalTime.of(12,0), LocalTime.of(20,0));
            OpeningHour sunday = new OpeningHour(DayOfWeek.SUNDAY,          LocalTime.of(12,0), LocalTime.of(20,0));
            openingHourRepository.saveAll(List.of(monday,tuesday,wednesday,thursday,friday,saturday,sunday));
        }
    }
}
