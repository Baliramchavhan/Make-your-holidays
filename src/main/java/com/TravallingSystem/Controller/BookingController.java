package com.TravallingSystem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TravallingSystem.EntityClas.Booking;
import com.TravallingSystem.EntityClas.User;
import com.TravallingSystem.Service.BookingService;
import com.TravallingSystem.Service.MailService;

import jakarta.servlet.http.HttpSession;

@Controller // Change RestController to Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MailService mailservice;

    @GetMapping("/register")
    public String showRegistrationForm1(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Thymeleaf template name
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model,
            @RequestParam(required = true) String email) {

        User existingUser = bookingService.FindByEmail(user.getEmail());

        if (existingUser != null) {
            // User already exists, add an error message to the model
            model.addAttribute("regError", "This email is already registered. Please use a different email.");
            return "register"; // Return to the registration page
        }

        bookingService.registerUser(user);
        mailservice.sendEmailReg(user.getEmail(), "Register Successfully  Travelling Agency ",
                " Welcome Aboard! 🌍✈️\r\n"
                        + "\r\n"
                        + "Congratulations on registering with us! You're now part of our travel community.\r\n"
                        + "\r\n"
                        + "Get ready to explore exciting destinations, discover hidden gems, and plan your next adventure.\r\n"
                        + "\r\n"
                        + "Happy travels! If you need any assistance, feel free to reach out!\r\n"
                        + "\r\n"
                        + "");
        return "index"; // Redirect to a success page
    }

    @GetMapping("/services")
    public String service(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/gallery")
    public String gllery(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/About")
    public String About(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/Home")
    public String Home(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/holidaypackage")
    public String Package(Model model) {
        model.addAttribute("user", new User());
        return "HolidayPackage";
    }

    @GetMapping("/Travel")
    public String Travel(Model model) {
        model.addAttribute("user", new User());
        return "Travel";
    }

    @GetMapping("/index")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Booking());
        return "index";
    }

    @GetMapping("/mounton")
    public String showMountoinForm(Model model) {
        model.addAttribute("user", new Booking());
        return "montain";
    }

    @GetMapping("/citytour")
    public String showcitetour(Model model) {
        model.addAttribute("use", new Booking());
        return "citytour";
    }

    @GetMapping("about")
    public String AboutUs(Model model) {
        return "About";
    }

    @GetMapping("/beach-paradise")
    public String Showbatch(Model model) {
        model.addAttribute("use", new Booking());
        return "beach-paradise";
    }

    @GetMapping("/help")
    public String HelpUs() {
        return "HelpUs";
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam(required = true) String email, @RequestParam String password,
            HttpSession session, Model model) {
        User user = bookingService.FindByEmail(email);
        if (user != null && password.equals(user.getPassword())) {
            session.setAttribute("user", user); // Store user in session
            return "index";
        }
        model.addAttribute("loginError", "Invalid email or password");
        return "login";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve user from session
        if (user != null) {
            model.addAttribute("user", user);
            return "profile";
        }
        return "redirect:/login"; // Redirect to login if no user is found
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User user, Model model) {
        bookingService.updateUser(user);
        return "redirect:/profile";
    }

    @GetMapping("/forgot")
    public String showForgotPasswordForm(Model model) {
        return "forgot"; // This refers to src/main/resources/templates/forgot-password.html
    }

    @PostMapping("/forgot")
    public String processForgotPassword(@RequestParam String email, Model model) {
        bookingService.initiatePasswordReset(email);
        model.addAttribute("message", "Your password resent sccessfully You Can Login With Your New Password");
        return "login";

    }

    @GetMapping("/reset")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset";
    }

    @PostMapping("/reset")
    public String processResetPassword(@RequestParam String token, @RequestParam String newPassword, Model model) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("error", "Token is missing.");
            return "reset";
        }

        boolean success = bookingService.resetPassword(token, newPassword);
        if (success) {
            model.addAttribute("message", "Password successfully reset. You can now log in.");
            return "login";
        } else {
            model.addAttribute("error", "Invalid or expired token.");
            return "reset";
        }
    }

}
