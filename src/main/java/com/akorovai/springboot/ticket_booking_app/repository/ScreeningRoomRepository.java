package com.akorovai.springboot.ticket_booking_app.repository;

import com.akorovai.springboot.ticket_booking_app.entities.ScreeningRoom;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface ScreeningRoomRepository extends JpaRepository<ScreeningRoom, Long> {
}
