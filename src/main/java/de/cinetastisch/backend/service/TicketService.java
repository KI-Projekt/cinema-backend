package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.TicketResponseDto;
import de.cinetastisch.backend.mapper.TicketMapper;
import de.cinetastisch.backend.repository.OrderRepository;
import de.cinetastisch.backend.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final OrderRepository orderRepository;


    public List<TicketResponseDto> getAllTickets(Long orderId) {
        if(orderId != null){
            return ticketMapper.entityToDto(ticketRepository.findAllByOrder(orderRepository.getReferenceById(orderId)));
        }
        return ticketMapper.entityToDto(ticketRepository.findAll());
    }

}
