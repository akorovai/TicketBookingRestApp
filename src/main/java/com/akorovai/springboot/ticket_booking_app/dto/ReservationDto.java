package com.akorovai.springboot.ticket_booking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private ScreeningForReservationDto screening;

    private LocalDateTime reservationTime;

    private LocalDateTime expirationTime;

    private String name;

    private String surname;

    private List<ReservationTicketDto> reservationTickets;

    private double amount;
}
