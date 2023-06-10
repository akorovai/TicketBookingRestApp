package com.akorovai.springboot.ticket_booking_app.Dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreeningForReservationDto {
    @NotNull
    private MovieDto movie;

    @NotNull
    private ScreeningRoomDto room;



    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
}
