package com.akorovai.springboot.ticket_booking_app.Dto;

import com.akorovai.springboot.ticket_booking_app.Entities.ReservationTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private ScreeningForReservationDto screening;

    private LocalDateTime reservationTime;

    private LocalDateTime expirationTime;

    private String name;

    private String surname;

    private List<ReservationTicketDto> reservationTickets;

    private double amount;
}
