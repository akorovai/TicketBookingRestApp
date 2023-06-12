package com.akorovai.springboot.ticket_booking_app.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    @NotNull
    private String title;
    @NotNull
    private Integer duration;
    @NotNull
    private String description;
}
