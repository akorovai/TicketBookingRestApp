package com.akorovai.springboot.ticket_booking_app.Ticket_types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationTicketType {
    ADULT("Adult", 25.0),
    STUDENT("Student", 18.0),
    CHILD("Child", 12.50);

    private final String displayName;
    private final double price;
}