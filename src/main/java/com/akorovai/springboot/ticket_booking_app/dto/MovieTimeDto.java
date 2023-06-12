package com.akorovai.springboot.ticket_booking_app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor

public class MovieTimeDto {

    @NotNull
    private String title;

    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    public MovieTimeDto(@NotNull String title, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}