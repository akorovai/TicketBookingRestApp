package com.akorovai.springboot.ticket_booking_app.Dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervalDto {
    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;
}
