package com.akorovai.springboot.ticket_booking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationTicketDto {

    private Long ticketId;

    private SeatDto seat;

    private String ticketType;

    private double amount;
}

