package com.rubinho.shishki.repository;

import com.rubinho.shishki.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(nativeQuery = true, name = "bookings_by_account")
    List<Booking> getAllBookingByAccount(@Param("p_account_id") Long accountId);

}
