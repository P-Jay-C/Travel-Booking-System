package com.amalitech.org.bookingservice.entity;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TypeAlias("AccommodationOffer")
@Document(collection = "accommodation_offers")
public class AccommodationOffer extends BookingOffer {
    private String locationCountry;
    private String locationCity;
    private String type; // House, Apartment, etc.
    private String capacity; // Single room, 2 bedrooms, etc.
    private Double price;
}

