//package com.TravallingSystem.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.TravallingSystem.EntityClas.Booking;
//import com.TravallingSystem.EntityClas.Payment;
//import com.TravallingSystem.repo.PaymentRepository;
//
//@Service
//public class PaymentService {
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//    public Payment processPayment(Booking booking, Double amount) {
//        Payment payment = new Payment();
//        payment.setAmount(amount);
//        payment.setPaymentDate(LocalDate.now());
//        payment.setBooking(booking);
//        return paymentRepository.save(payment);
//    }
//}
//
