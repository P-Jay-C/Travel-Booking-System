package com.amalitech.org.categoryservice.entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    private String id;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Code should only contain alphanumeric characters")
    private String code;

    private String icon;

//    private List<BookingOffer> bookingOffers;

    @CreatedBy
    private AppUser createdBy;
}

