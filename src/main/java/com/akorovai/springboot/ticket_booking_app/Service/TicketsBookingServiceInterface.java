package com.akorovai.springboot.ticket_booking_app.Service;

import com.akorovai.springboot.ticket_booking_app.Dto.*;
import com.akorovai.springboot.ticket_booking_app.Entities.Reservation;

import java.util.List;

public interface TicketsBookingServiceInterface {
    List<DateTimeDto> getDateTimes();
    List<MovieTimeDto> getMovies(IntervalDto intervalDto);

    ScreeningDto getScreeningWithHallAndSeats(Long idScreening);

    List<SeatDto> getAvailableSeatsForScreening(Long idScreening);

    Reservation addReservation(AddReservationDto reservationDao, Long idScreening);

    ReservationDto getReservation(Long idScreening);
}
