package com.TravallingSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    // Regex to validate email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$");

    public void sendEmailReg(String to, String subject, String text) {
        // Trim and validate the email address
        to = to.trim();
        if (!isValidEmail(to)) {
            throw new IllegalArgumentException("Invalid email format: " + to);
        }

        // Create the email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("your-email@example.com"); // Set from address

        try {
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (MailSendException e) {
            // Handle specific mail sending exceptions
            System.err.println("Error sending email: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
