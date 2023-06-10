package com.akorovai.springboot.ticket_booking_app.Dto;

import com.akorovai.springboot.ticket_booking_app.ErrorHandling.InvalidReservationException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReservationDto {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private List<Ticket> tickets;

    public void validateReservation() {
        if (!name.matches("[A-Z][a-zA-Z]{2,}")) {
            throw new InvalidReservationException("Invalid name: " + name+"\n" +
                    "Name should each be at least three characters long");
        }
        if (!surname.matches("[A-Z][a-zA-Z]+(-[A-Z][a-zA-Z]+)?")) {
            throw new InvalidReservationException("Invalid surname: " + surname +"\n" +
                    "Surname should each be at least three characters long");
        }
        if (tickets.isEmpty()) {
            throw new InvalidReservationException("Invalid reservation: No seats selected." +
                    " At least one seat must be chosen.");
        }
    }
}
