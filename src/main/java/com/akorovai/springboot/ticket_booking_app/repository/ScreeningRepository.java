package com.akorovai.springboot.ticket_booking_app.repository;

import com.akorovai.springboot.ticket_booking_app.dto.MovieTimeDto;
import com.akorovai.springboot.ticket_booking_app.dto.SeatDto;
import com.akorovai.springboot.ticket_booking_app.entities.Screening;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Lazy
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @Query("SELECT new com.akorovai.springboot.ticket_booking_app.dto.MovieTimeDto(m.title, s.startTime, s.endTime) " +
            "FROM Screening s " +
            "JOIN s.movie m " +
            "ORDER BY m.title, s.startTime")
    List<MovieTimeDto> getMovieScreeningsOrderByTitleAndStartTime();

    @Query("SELECT new com.akorovai.springboot.ticket_booking_app.dto.SeatDto(st.seatId, st.seatNumber) " +
            "FROM Screening s " +
            "JOIN s.room r " +
            "JOIN r.seats st " +
            "LEFT JOIN st.reservationTickets rt " +
            "WHERE s.idScreening = :screeningId " +
            "AND rt IS NULL")
    List<SeatDto> getAvailableSeatsByScreeningId(Long screeningId);

    @Query("SELECT " +
            "m.title, m.duration, m.description, " +
            "sr.name, " +
            "s.startTime, " +
            "s.endTime " +
            "FROM Screening s " +
            "JOIN s.movie m " +
            "JOIN s.room sr " +
            "WHERE s.idScreening = :screeningId")
    List<Object[]> getScreenings(@Param("screeningId") Long screeningId);


}
