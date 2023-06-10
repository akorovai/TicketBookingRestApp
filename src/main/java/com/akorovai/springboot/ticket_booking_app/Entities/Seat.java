package com.akorovai.springboot.ticket_booking_app.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ScreeningRoom room;

    @Column(nullable = false)
    private String seatNumber;



    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<ReservationTicket> reservationTickets;
}
