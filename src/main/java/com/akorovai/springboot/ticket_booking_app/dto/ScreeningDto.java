package com.akorovai.springboot.ticket_booking_app.dto;



import jakarta.validation.constraints.NotNull;
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
public class ScreeningDto {

    @NotNull
    private MovieDto movie;

    @NotNull
    private ScreeningRoomDto room;

    private List<SeatDto> seats;

    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
}
