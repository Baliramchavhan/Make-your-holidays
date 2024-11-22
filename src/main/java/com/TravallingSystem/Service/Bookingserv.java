package com.TravallingSystem.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.TravallingSystem.EntityClas.Booking;

import com.TravallingSystem.repo.BookingRepository;

@Service
public class Bookingserv {
    @Autowired
    private BookingRepository bookingRepository;
    // @Autowired
    // private TicketService ticketService;
    @Autowired
    private JavaMailSender maileSender;

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void sendBookingConfirmation(String to, String name, String startDestination, String endDestination,
            LocalDateTime localDateTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Travel Ticket Booking Confirmation");
        message.setText("Dear " + name + ",\n\n" +
                "Thank you for choosing us for your travel plans!\n" +
                "We're excited to confirm your booking with the following details:\n\n" +
                "Departure: " + startDestination + "\n" +
                "Arrival: " + endDestination + "\n" +
                "Booking Date: " + localDateTime + "\n\n" +
                "We wish you a pleasant journey!\n" +
                "Best wishes,\n" +
                "Your Travel Agency Team");

        maileSender.send(message);
    }

    public void sendCancellationEmail(String toEmail, String userName, Long ticketId,
            boolean isCancelled) {
        String subject = isCancelled ? "Your Ticket Has Been Successfully Cancelled"
                : "Ticket Cancellation Unsuccessful";
        String messageBody = isCancelled
                ? "Dear " + userName
                        + ",\n\nWe are writing to confirm that your ticket for  has been successfully cancelled.\n\n" +
                        "Ticket ID: " + ticketId + "\n" +

                        "Cancellation Date: " + new java.util.Date() + "\n\n" +
                        "Thank you for choosing us!\n\nBest regards,\n[Make your Holiday]\n[9373918434]"
                : "Dear " + userName
                        + ",\n\nWe regret to inform you that we were unable to cancel your ticket for The cancellation could not be processed because:\n"
                        +
                        "Ticket ID: " + ticketId + " was not found, or\n" +
                        "The ticket has already been cancelled.\n\n" +
                        "Please contact support if you have any questions.\n\nBest regards,\n[Make Your Holiday]";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(messageBody);

        // Send the email
        maileSender.send(message);
    }

    public Optional<Booking> findBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public boolean cancelBooking(Long id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus(Booking.BookingStatus.CANCELED); // Change status
            bookingRepository.save(booking);
            return true;
        }
        return false; // Booking not found
    }

    public boolean cancelTicket(Long ticketId, String userEmail) {
        Booking ticket = bookingRepository.findByIdAndEmail(ticketId, userEmail);
        if (ticket != null && !ticket.isCancelled()) {
            ticket.setCancelled(true);
            bookingRepository.save(ticket);
            return true;
        }
        return false; // Ticket not found or already cancelled
    }

}
