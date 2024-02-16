package com.amalitech.org.bookingservice.entity;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TypeAlias("FlightOffer")
@Document(collection = "flight_offers")

public class FlightOffer extends BookingOffer {
    private String departureCity;
    private String destinationCity;
    private LocalDate date;
    private Double price;
}

