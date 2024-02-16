package com.amalitech.org.bookingservice.service;

import com.amalitech.org.bookingservice.entity.*;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface BookingOfferService {
    BookingOffer getOfferById(ObjectId id);

    AccommodationOffer createAccommodationOffer(AccommodationOffer accommodationOffer);
    HotelOffer createHotelOffer(HotelOffer hotelOffer);
    FlightOffer createFlightOffer(FlightOffer flightOffer);
    CarRentalOffer createCarRentalOffer(CarRentalOffer carRentalOffer);

    List<BookingOffer> getAllBookingOffers();
}

