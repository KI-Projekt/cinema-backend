package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.TicketFare;
import de.cinetastisch.backend.repository.TicketFareRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketFareControllerTest {

    @InjectMocks
    TicketFareController ticketFareController;
    @Mock
    Specification<TicketFare> spec;

    @Mock
    TicketFareRepository ticketFareRepository;

    @Test
    void getAll() {
        TicketFare ticketFare = new TicketFare(null, null, 3.0, null);
        List<TicketFare> ticketFareList = List.of(ticketFare, ticketFare);
        when(ticketFareRepository.findAll(spec)).thenReturn(ticketFareList);
        ResponseEntity<?> response = ticketFareController.getAll(spec);

        assertEquals(ticketFareList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getOneById() {
        TicketFare ticketFare = new TicketFare(null, null, 3.0, null);
        when(ticketFareRepository.getReferenceById((long) 1.2)).thenReturn(ticketFare);
        ResponseEntity<?> response = ticketFareController.getOneById((long) 1.2);
        assertEquals(ticketFare, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void addOne() {
        TicketFare ticketFare = new TicketFare(null, null, 3.0, null);
        when(ticketFareRepository.save(ticketFare)).thenReturn(ticketFare);
        ResponseEntity<?> response = ticketFareController.addOne(ticketFare);
        assertEquals(ticketFare, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());


    }

    @Test
    void replaceOne() {
        TicketFare ticketFare = new TicketFare((long) 1.2, null, 3.0, null);
        when(ticketFareRepository.save(ticketFare)).thenReturn(ticketFare);
        ResponseEntity<?> response = ticketFareController.replaceOne((long) 1.2, ticketFare);
        assertEquals(ticketFare, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }

    @Test
    void replaceOneexeption() {
        TicketFare ticketFare = new TicketFare(null, null, 3.0, null);
        assertThrows(IllegalArgumentException.class, () -> ticketFareController.replaceOne((long) 1.3, ticketFare));

    }

    @Test
    void deleteOne() {
        ResponseEntity<?> response = ticketFareController.deleteOne((long) 1.2);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}