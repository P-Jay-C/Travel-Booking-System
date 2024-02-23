package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "image"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String description;
    private int currentQuantity;
    private double costPrice;
    private double salePrice;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
    private boolean is_activated;
    private boolean is_deleted;
    private String type;

    // AccommodationOffer fields
    private String locationCountry;
    private String locationCity;
    private String accommodationType;
    private String capacity;
    private Double accommodationPrice;

    // CarRentalOffer fields
    private String carType;
    private Double carRentalPricePerDay;

    // FlightOffer fields
    private String departureCity;
    private String destinationCity;
    private LocalDate flightDate;
    private Double flightPrice;

    // HotelOffer fields
    private String hotelName;
    private String hotelLocation;
    private String roomTypes;
    private Double hotelPrice;
}

