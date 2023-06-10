package com.akorovai.springboot.ticket_booking_app.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "screening_id", referencedColumnName = "screening_id")
    private Screening screening;

    @Column(nullable = false)
    private LocalDateTime reservationTime;
    @Column(nullable = false)
    private LocalDateTime expirationTime;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationTicket> reservationTickets;

    @Column(nullable = false)
    private double amount;
}
