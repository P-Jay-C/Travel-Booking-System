package com.ecommerce.library.dto;

import com.ecommerce.library.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int currentQuantity;
    private double costPrice;
    private double salePrice;
    private String image;
    private String type;
    private Category category;
    private boolean activated;
    private boolean deleted;
    private String currentPage;

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
