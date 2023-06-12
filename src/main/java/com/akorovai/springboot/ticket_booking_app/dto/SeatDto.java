package com.akorovai.springboot.ticket_booking_app.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
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
