package com.akorovai.springboot.ticket_booking_app.service;

import com.akorovai.springboot.ticket_booking_app.dto.*;
import com.akorovai.springboot.ticket_booking_app.entities.Reservation;

import java.util.List;

public interface TicketsBookingServiceInterface {
    List<DateTimeDto> getDateTimes();
    List<MovieTimeDto> getMovies(IntervalDto intervalDto);

    ScreeningDto getScreeningWithHallAndSeats(Long idScreening);

    List<SeatDto> getAvailableSeatsForScreening(Long idScreening);

    Reservation addReservation(AddReservationDto reservationDao, Long idScreening);

    ReservationDto getReservation(Long idScreening);
}
