package com.amalitech.org.bookingservice.entity;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TypeAlias("CarRentalOffer")
@Document(collection = "car_rental_offers")
public class CarRentalOffer extends BookingOffer {
    private String carType;

    @Field("pricePerDay")
    private Double price;
    private String carImage; // Assuming a URL or path to the image
}
