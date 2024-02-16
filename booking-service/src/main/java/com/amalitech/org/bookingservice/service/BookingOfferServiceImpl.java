package com.amalitech.org.bookingservice.service;
import com.amalitech.org.bookingservice.Exceptions.ObjectNotFoundException;
import com.amalitech.org.bookingservice.entity.*;
import com.amalitech.org.bookingservice.repository.BookingOfferRepository;
import com.amalitech.org.bookingservice.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class BookingOfferServiceImpl implements BookingOfferService {
    @Autowired
    private BookingOfferRepository bookingOfferRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public BookingOffer getOfferById(ObjectId id) {
        return bookingOfferRepository.findById(String.valueOf(id)).orElseThrow(() -> new ObjectNotFoundException("booking", String.valueOf(id)));
    }

    @Override
    public AccommodationOffer createAccommodationOffer(AccommodationOffer accommodationOffer) {
        return bookingOfferRepository.save(accommodationOffer);
    }

    @Override
    public HotelOffer createHotelOffer(HotelOffer hotelOffer) {
        return bookingOfferRepository.save(hotelOffer);
    }

    @Override
    public FlightOffer createFlightOffer(FlightOffer flightOffer) {
        return bookingOfferRepository.save(flightOffer);
    }

    @Override
    public CarRentalOffer createCarRentalOffer(CarRentalOffer carRentalOffer) {
        return bookingOfferRepository.save(carRentalOffer);
    }

    @Override
    public List<BookingOffer> getAllBookingOffers() {
        return bookingOfferRepository.findAll();
    }

}


