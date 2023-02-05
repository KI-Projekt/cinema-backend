package de.cinetastisch.backend;

import de.cinetastisch.backend.model.TicketFare;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.TicketFareRepository;
import de.cinetastisch.backend.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private TicketFareRepository ticketFareRepository;

    public DataLoader(UserRepository userRepository, PasswordEncoder encoder, TicketFareRepository ticketFareRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.ticketFareRepository = ticketFareRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(ticketFareRepository.findAll().size() > 0 ){
            return;
        }

        TicketFare fare1 = new TicketFare("Adult", 10.0, "Über 18");
        ticketFareRepository.save(fare1);

        if(userRepository.findAll().size() > 0)
            return;

        alreadySetup = true;
//
//        SecurityPrivilege read_privilege = new SecurityPrivilege("READ_PRIVILEGE");
//        SecurityPrivilege write_privilege = new SecurityPrivilege("WRITE_PRIVILEGE");
//
//        SecurityRole user_role = new SecurityRole("USER_ROLE", List.of(read_privilege));
//        SecurityRole admin_role = new SecurityRole("ADMIN_ROLE", List.of(read_privilege, write_privilege));

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
}
