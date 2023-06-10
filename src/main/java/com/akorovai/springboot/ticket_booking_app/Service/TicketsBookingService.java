package com.akorovai.springboot.ticket_booking_app.Service;

import com.akorovai.springboot.ticket_booking_app.Dto.*;
import com.akorovai.springboot.ticket_booking_app.Entities.*;

import com.akorovai.springboot.ticket_booking_app.ErrorHandling.InvalidReservationException;
import com.akorovai.springboot.ticket_booking_app.Repository.ReservationTicketsRepository;
import com.akorovai.springboot.ticket_booking_app.Repository.ScreeningRepository;
import com.akorovai.springboot.ticket_booking_app.Repository.SeatRepository;
import com.akorovai.springboot.ticket_booking_app.Repository.ReservationRepository;
import com.akorovai.springboot.ticket_booking_app.Ticket_types.ReservationTicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
public class TicketsBookingService implements TicketsBookingServiceInterface {
    private ScreeningRepository screeningRepository;
    private SeatRepository seatRepository;
    private ReservationRepository reservationRepository;
    private ReservationTicketsRepository ticketsRepository;

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

        if (startTime == null || endTime == null) {
            throw new InvalidReservationException("Start time and end time must be provided");
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

        List<ReservationTicket> reservationTickets = new ArrayList<>();
        double totalAmount = 0.0;

        Reservation reservation = new Reservation();
        reservation.setName(reservationDao.getName());
        reservation.setSurname(reservationDao.getSurname());
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setExpirationTime(LocalDateTime.now().plusMinutes(30));
        reservation.setScreening(screening);

        for (Ticket ticket : reservationDao.getTickets()) {
            Seat seat = seatRepository.findById(ticket.getSeatId())
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            ReservationTicketType reservationTicketType = ReservationTicketType.valueOf(ticket.getTicketType());
            double ticketPrice = reservationTicketType.getPrice();

            ReservationTicket reservationTicket = new ReservationTicket();
            reservationTicket.setSeat(seat);
            reservationTicket.setReservation(reservation);
            reservationTicket.setTicketType(reservationTicketType.getDisplayName());
            reservationTicket.setAmount(ticketPrice);

            reservationTickets.add(reservationTicket);
            totalAmount += ticketPrice;
        }

        reservation.setReservationTickets(reservationTickets);
        reservation.setAmount(totalAmount);

        return reservationRepository.save(reservation);
    }

    @Override
    public ReservationDto getReservation(Long idScreening) {
        Reservation reservation = reservationRepository.findById(idScreening)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setName(reservation.getName());
        reservationDto.setSurname(reservation.getSurname());
        reservationDto.setReservationTime(reservation.getReservationTime());
        reservationDto.setExpirationTime(reservation.getExpirationTime());
        reservationDto.setAmount(reservation.getAmount());
        List<ReservationTicket> list = ticketsRepository.findAll().stream()
                .filter(e -> e.getReservation().getReservationId() == reservation.getReservationId())
                .toList();

        List<ReservationTicketDto> reservationTicketDtos = list.stream()
                .map(reservationTicket -> {
                    ReservationTicketDto reservationTicketDto = new ReservationTicketDto();
                    reservationTicketDto.setTicketId(reservationTicket.getTicketId());
                    reservationTicketDto.setSeat(new SeatDto(reservationTicket.getSeat().getSeatId(), reservationTicket.getSeat().getSeatNumber()));
                    reservationTicketDto.setTicketType(reservationTicket.getTicketType());
                    reservationTicketDto.setAmount(reservationTicket.getAmount());
                    return reservationTicketDto;
                })
                .toList();

        reservationDto.setReservationTickets(reservationTicketDtos);


        Screening screening = reservation.getScreening();
        ScreeningForReservationDto screeningDto = new ScreeningForReservationDto();
        screeningDto.setStartTime(screening.getStartTime());
        screeningDto.setEndTime(screening.getEndTime());

        Movie movie = screening.getMovie();
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle(movie.getTitle());
        movieDto.setDuration(movie.getDuration());
        movieDto.setDescription(movie.getDescription());

        screeningDto.setMovie(movieDto);

        ScreeningRoom room = screening.getRoom();
        ScreeningRoomDto roomDto = new ScreeningRoomDto();
        roomDto.setName(room.getName());

        screeningDto.setRoom(roomDto);

        reservationDto.setScreening(screeningDto);

        return reservationDto;
    }
}
