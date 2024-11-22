//package com.TravallingSystem.Service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import com.TravallingSystem.EntityClas.Ticket;
//
//import com.TravallingSystem.repo.TicketRepository;
//
//@Service
//public class TicketService {
//    @Autowired
//    private TicketRepository ticketRepository;
//
//    public List<Ticket> findAllTickets() {
//        return ticketRepository.findAll();
//    }
//
//    public Ticket findTicketById(Long id) {
//        return ticketRepository.findById(id).orElse(null);
//    }
//
//    public void saveTicket(Ticket ticket) {
//        ticketRepository.save(ticket);
//    }
//}
//
