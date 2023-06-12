package com.akorovai.springboot.ticket_booking_app.service;

import com.akorovai.springboot.ticket_booking_app.dto.*;
import com.akorovai.springboot.ticket_booking_app.entities.*;
import com.akorovai.springboot.ticket_booking_app.error_handling.InvalidReservationException;
import com.akorovai.springboot.ticket_booking_app.repository.ReservationRepository;
import com.akorovai.springboot.ticket_booking_app.repository.ReservationTicketsRepository;
import com.akorovai.springboot.ticket_booking_app.repository.ScreeningRepository;
import com.akorovai.springboot.ticket_booking_app.repository.SeatRepository;
import com.akorovai.springboot.ticket_booking_app.ticket_types.ReservationTicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Lazy
public class TicketsBookingService implements TicketsBookingServiceInterface {
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationTicketsRepository ticketsRepository;


    @Autowired
    public TicketsBookingService(ScreeningRepository screeningRepository, SeatRepository seatRepository,
                                 ReservationRepository reservationRepository, ReservationTicketsRepository ticketsRepository) {
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.ticketsRepository = ticketsRepository;
    }

    @Override
    public List<DateTimeDto> getDateTimes() {
        return screeningRepository.findAll().stream()
                .map(e -> new DateTimeDto(e.getStartTime()))
                .toList();
    }

    @Override
    public List<MovieTimeDto> getMovies(IntervalDto intervalDto) {
        LocalDateTime startTime = intervalDto.getStartTime();
        LocalDateTime endTime = intervalDto.getEndTime();

        if (Objects.isNull(startTime)|| Objects.isNull(endTime)) {
            throw new InvalidReservationException("Start time and end time must be provided");
        }
        if (intervalDto.getStartTime().isAfter(intervalDto.getEndTime())) {
            throw new InvalidReservationException("Start time must be before end time");
        }

        return screeningRepository.getMovieScreeningsOrderByTitleAndStartTime().stream()
                .filter(screening ->
                        screening.getStartTime().isAfter(intervalDto.getStartTime())
                                && screening.getEndTime().isBefore(intervalDto.getEndTime()))
                .toList();
    }

    @Override
    public ScreeningDto getScreeningWithHallAndSeats(Long idScreening) {
        List<SeatDto> seats = screeningRepository.getAvailableSeatsByScreeningId(idScreening);
        return screeningRepository.getScreenings(idScreening).stream()
                .map(e -> new ScreeningDto(
                        new MovieDto((String) e[0], (int) e[1], (String) e[2]),
                        new ScreeningRoomDto((String) e[3]),
                        seats,
                        (LocalDateTime) e[4],
                        (LocalDateTime) e[5]))
                .findFirst()
                .orElseThrow(() -> new InvalidReservationException("Screening not found"));
    }

    @Override
    public List<SeatDto> getAvailableSeatsForScreening(Long idScreening) {
        return screeningRepository.getAvailableSeatsByScreeningId(idScreening);
    }



    @Override
    public Reservation addReservation(AddReservationDto reservationDao, Long idScreening) {
        reservationDao.validateReservation();

        Screening screening = screeningRepository.findById(idScreening)
                .orElseThrow(() -> new RuntimeException("Screening not found"));

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime bookingLimit = screening.getStartTime().minusMinutes(15);
        if (currentDateTime.isAfter(bookingLimit)) {
            throw new InvalidReservationException("Seats cannot be booked within 15 minutes of the screening");
        }

        List<Ticket> tickets = reservationDao.getTickets();
        if (tickets.stream().map(ticket -> seatRepository.findById(ticket.getSeatId())
                        .orElseThrow(() -> new RuntimeException("Seat not found")))
                .anyMatch(seat -> isSeatAlreadyBooked(screening, seat))) {
            throw new InvalidReservationException("One or more seats are already booked for this screening");
        }

        List<ReservationTicket> reservationTickets = new ArrayList<>();
        double totalAmount = 0.0;

        Reservation reservation = Reservation.builder()
                .name(reservationDao.getName())
                .surname(reservationDao.getSurname())
                .reservationTime(LocalDateTime.now())
                .expirationTime(LocalDateTime.now().plusMinutes(30))
                .screening(screening)
                .build();

        for (Ticket ticket : tickets) {
            Seat seat = seatRepository.findById(ticket.getSeatId())
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            ReservationTicketType reservationTicketType = ReservationTicketType.valueOf(ticket.getTicketType());
            double ticketPrice = reservationTicketType.getPrice();

            ReservationTicket reservationTicket = ReservationTicket.builder()
                    .seat(seat)
                    .reservation(reservation)
                    .ticketType(reservationTicketType.getDisplayName())
                    .amount(ticketPrice)
                    .build();

            reservationTickets.add(reservationTicket);
            totalAmount += ticketPrice;
        }

        reservation.setReservationTickets(reservationTickets);
        reservation.setAmount(totalAmount);

        return reservationRepository.save(reservation);
    }

    public boolean isSeatAlreadyBooked(Screening screening, Seat seat) {
        return ticketsRepository.findAll()
                .stream()
                .filter(ticket -> ticket.getReservation().getScreening().equals(screening))
                .map(ReservationTicket::getSeat)
                .anyMatch(ticketSeat -> ticketSeat != null && ticketSeat.getSeatId().equals(seat.getSeatId()));
    }





    @Override
    public ReservationDto getReservation(Long idScreening) {
        Reservation reservation = reservationRepository.findById(idScreening)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return ReservationDto.builder()
                .name(reservation.getName())
                .surname(reservation.getSurname())
                .reservationTime(reservation.getReservationTime())
                .expirationTime(reservation.getExpirationTime())
                .amount(reservation.getAmount())
                .reservationTickets(ticketsRepository.findAll().stream()
                        .filter(e -> e.getReservation().getReservationId().equals(reservation.getReservationId()))
                        .map(reservationTicket -> ReservationTicketDto.builder()
                                .ticketId(reservationTicket.getTicketId())
                                .seat(SeatDto.builder()
                                        .seatId(reservationTicket.getSeat().getSeatId())
                                        .seatNumber(reservationTicket.getSeat().getSeatNumber())
                                        .build())
                                .ticketType(reservationTicket.getTicketType())
                                .amount(reservationTicket.getAmount())
                                .build())
                        .toList())
                .screening(ScreeningForReservationDto.builder()
                        .startTime(reservation.getScreening().getStartTime())
                        .endTime(reservation.getScreening().getEndTime())
                        .movie(MovieDto.builder()
                                .title(reservation.getScreening().getMovie().getTitle())
                                .duration(reservation.getScreening().getMovie().getDuration())
                                .description(reservation.getScreening().getMovie().getDescription())
                                .build())
                        .room(ScreeningRoomDto.builder()
                                .name(reservation.getScreening().getRoom().getName())
                                .build())
                        .build())
                .build();
    }
}
