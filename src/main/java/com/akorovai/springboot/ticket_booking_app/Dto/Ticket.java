package com.akorovai.springboot.ticket_booking_app.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @NotNull
    private Integer seatId;

    @NotNull
    private String ticketType;
}
