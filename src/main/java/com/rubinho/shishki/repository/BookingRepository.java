package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(Account user);
}
