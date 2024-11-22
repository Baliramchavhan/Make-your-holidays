package com.TravallingSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TravallingSystem.EntityClas.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByIdAndEmail(Long id, String userEmail);
}
