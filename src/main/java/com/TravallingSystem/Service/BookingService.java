package com.TravallingSystem.Service;



import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.TravallingSystem.EntityClas.Booking;
import com.TravallingSystem.EntityClas.PasswordResetToken;
import com.TravallingSystem.EntityClas.User;
import com.TravallingSystem.repo.BookingRepository;
import com.TravallingSystem.repo.PasswordResetTokenRepository;
import com.TravallingSystem.repo.UserRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    
    
    public User registerUser(User user) {
       
            return userRepository.save(user);
        
    }

   
    
        public void sendEmail(String to, String subject, String text) {
            SimpleMailMessage message = new SimpleMailMessage();
              message.setTo(to);
              message.setSubject(subject);
              message.setText(text);
            
            try {
                mailSender.send(message);
            } catch (MailSendException e) {
                // Handle the exception and log it
                System.out.println("Error sending email: " + e.getMessage());
            }
        }
        
        public void updateUser(User user) {
            userRepository.save(user);
        }
        
        // Delete a user by their ID
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }
      
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    public User FindByEmail(String email) {
    	return userRepository.findByEmail(email);
    }
    // Initiate a password reset request
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setEmail(email);
            resetToken.setUserName(user.getUsername());
            tokenRepository.save(resetToken);

            String resetLink = String.format("http://localhost:8080/bookings/reset?token=%s", token);
                sendEmail(email, "Password Reset Request", 
                String.format("To reset your password, click the link below:\n%s", resetLink));
        } else {
            // Consider logging this event or throwing a custom exception
        }
    }

    // Reset the user's password using the token
    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken != null) {
            User user = userRepository.findByEmail(resetToken.getEmail());
            if (user != null) {
                user.setPassword(newPassword); // No encoding, just save the new password
                userRepository.save(user);
                tokenRepository.delete(resetToken);
                return true;
            }
        }
        return false;
    }
    
   

}
