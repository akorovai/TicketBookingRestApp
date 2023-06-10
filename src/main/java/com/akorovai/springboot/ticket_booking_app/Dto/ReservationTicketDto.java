package com.akorovai.springboot.ticket_booking_app.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTicketDto {

    private Long ticketId;

    private SeatDto seat;

    private String ticketType;

    private double amount;
}

