package com.amalitech.org.bookingservice.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booking_offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias("BookingOffer")
public abstract class BookingOffer {
    @Id
    private String id;
    @DBRef
    private Category category;
}

