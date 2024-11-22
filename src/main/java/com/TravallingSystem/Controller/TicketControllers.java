package com.TravallingSystem.Controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TravallingSystem.EntityClas.Booking;
import com.TravallingSystem.Service.Bookingserv;
//import com.TravallingSystem.Service.PaymentService;
//import com.TravallingSystem.Service.TicketService;
//import com.TravallingSystem.repo.UserRepository;

@Controller
// @RequestMapping("/bookings")
public class TicketControllers {

    @Autowired
    private Bookingserv bookingService;

    @GetMapping("/books")
    public String showRegistrationForm1(Model model) {
        model.addAttribute("booking", new Booking());
        return "book";
    }

    @PostMapping("/books")
    public String bookTicket(Booking booking) {

        // Proceed with saving the booking
        bookingService.saveBooking(booking);

        bookingService.sendBookingConfirmation(booking.getEmail(), booking.getName(), booking.getStartDestination(),
                booking.getEndDestination(), booking.getCreationDate());
        return "recipt"; // Change this to your actual success view
    }

    @RequestMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadTicket(Long bookingId) {
        Optional<Booking> bookingOpt = bookingService.findBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            String ticketDetails = "Ticket Details:\n"
                    + "Id: " + booking.getId() + "\n"
                    + "Name: " + booking.getName() + "\n"
                    + "Email: " + booking.getEmail() + "\n"
                    + "Starting Point: " + booking.getStartDestination() + "\n"
                    + "End Point: " + booking.getEndDestination() + "\n"
                    + "Number of Tickets: " + booking.getNumberOfTickets();

            ByteArrayResource resource = new ByteArrayResource(ticketDetails.getBytes(StandardCharsets.UTF_8));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=ticket.text")
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(resource.contentLength())
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cancel")
    public String cancelTicket(@RequestParam Long bookingId, Model model) {
        boolean canceled = bookingService.cancelBooking(bookingId);
        model.addAttribute("cancellationSuccess", canceled);
        return "cancel"; // Thymeleaf template for cancellation confirmation
    }

    @GetMapping("/cancel-ticket")
    public String cancleticket() {
        return "cancel-ticket"; // Display cancel ticket page
    }

    @PostMapping("/cancel-ticket")
    public String cancelTicket(@RequestParam Long ticketId, @RequestParam String userEmail,
            @RequestParam String userName, Model model) {
        boolean isCancelled = bookingService.cancelTicket(ticketId, userEmail);

        // Add a message to the model based on the cancellation outcome
        if (isCancelled) {
            model.addAttribute("message", "Ticket cancelled successfully.");
            bookingService.sendCancellationEmail(userEmail, userName, ticketId,
                    isCancelled);

        } else {
            model.addAttribute("message", "Ticket not found or already cancelled.");
            bookingService.sendCancellationEmail(userEmail, userName, ticketId,
                    isCancelled);
        }

        return "cancel-ticket"; // Return to the same page with the message
    }

}
