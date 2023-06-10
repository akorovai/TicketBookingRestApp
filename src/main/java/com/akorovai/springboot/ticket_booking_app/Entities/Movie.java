package com.akorovai.springboot.ticket_booking_app.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long idMovie;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int duration;


    @Column(nullable = false)
    private String description;


}