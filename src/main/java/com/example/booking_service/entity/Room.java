package com.example.booking_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "rooms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "room_number")
    private Integer roomNumber;

    private BigDecimal price;

    @Column(name = "max_num_persons")
    private Integer maxNumberOfPeople;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @ToString.Exclude
    private Hotel hotel;

    @Builder.Default
    private List<LocalDate> bookedDates = new ArrayList<>();

    public void addBookedDates(List<LocalDate> dates) {
        bookedDates.addAll(dates);
    }

}
