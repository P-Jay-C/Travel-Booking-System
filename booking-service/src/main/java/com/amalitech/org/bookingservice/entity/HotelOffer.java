package com.amalitech.org.bookingservice.entity;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TypeAlias("HotelOffer")
@Document(collection = "hotel_offers")
public class HotelOffer extends BookingOffer {
    private String hotelName;
    private String location;
    private List<String> roomTypes;
    private Double price;
}

