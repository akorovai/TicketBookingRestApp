package com.akorovai.springboot.ticket_booking_app.Dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeatDto {
    @NotNull
    private Long seatId;
    @NotNull
    private String seatNumber;


    public SeatDto(Long seatId, String seatNumber) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
    }
}