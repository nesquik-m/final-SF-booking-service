package com.example.booking_service.entity;

import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/*
Вопросы куратору:
1) private Double rating; // По заданию - (от 1 до 5). Реальный рейтинг обычно состоит из дробных чисел.

*/

@Entity(name = "hotels")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String title;

    private String city;

    private String address;

    private Double distance;

    private Double rating;

    @Column(name = "number_of_ratings")
    private Integer numberOfRatings;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    private List<Room> rooms = new ArrayList<>();

}
