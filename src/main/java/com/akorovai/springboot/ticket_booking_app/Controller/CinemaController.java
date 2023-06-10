package com.akorovai.springboot.ticket_booking_app.Controller;

import com.akorovai.springboot.ticket_booking_app.Dto.*;
import com.akorovai.springboot.ticket_booking_app.Entities.Reservation;
import com.akorovai.springboot.ticket_booking_app.ErrorHandling.InvalidReservationException;
import com.akorovai.springboot.ticket_booking_app.Service.TicketsBookingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cinema")
@Lazy
public class CinemaController {
    private final TicketsBookingServiceInterface bookingService;

    @Autowired
    public CinemaController(TicketsBookingServiceInterface bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/dates")
    private ResponseEntity<?> getDateTimes() {
        List<DateTimeDto> dateTimes = bookingService.getDateTimes();
        if (dateTimes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No date times found.");
        }
        return ResponseEntity.ok(dateTimes);
    }

    @GetMapping("/movies")
    private ResponseEntity<?> getMovies(@RequestBody IntervalDto interval) {
        List<MovieTimeDto> movies = bookingService.getMovies(interval);
        if (movies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No movies found for the given interval.");
        }
        return ResponseEntity.ok(movies);
    }

    @GetMapping("screening/{IdScreening}")
    private ResponseEntity<?> getScreeningDetails(@PathVariable Long IdScreening) {
        try {
            ScreeningDto screeningWithHallAndSeats = bookingService.getScreeningWithHallAndSeats(IdScreening);
            return ResponseEntity.ok(screeningWithHallAndSeats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("screening/{IdScreening}/seats")
    private ResponseEntity<?> getAvailableSeats(@PathVariable Long IdScreening) {
        List<SeatDto> availableSeats = bookingService.getAvailableSeatsForScreening(IdScreening);
        if (availableSeats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No available seats found for the screening.");
        }
        return ResponseEntity.ok(availableSeats);
    }

    @PutMapping("screening/reservation/{IdScreening}")
    private ResponseEntity<?> addReservation(@RequestBody AddReservationDto reservationDao, @PathVariable Long IdScreening) {
        try {
            Reservation reservation = bookingService.addReservation(reservationDao, IdScreening);
            return ResponseEntity.ok("Added new Reservation " + reservation.getReservationId());
        } catch (InvalidReservationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("screening/reservation/{IdScreening}")
    private ResponseEntity<?> getReservationInformation(@PathVariable Long IdScreening) {
        try {
            ReservationDto reservation = bookingService.getReservation(IdScreening);
            return ResponseEntity.ok(reservation);
        } catch (InvalidReservationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
