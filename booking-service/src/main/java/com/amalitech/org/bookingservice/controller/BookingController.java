package com.amalitech.org.bookingservice.controller;
import com.amalitech.org.bookingservice.entity.*;
import com.amalitech.org.bookingservice.service.BookingOfferService;
import com.amalitech.org.bookingservice.service.CategoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bookingOffers")
public class BookingController {
    @Autowired
    private BookingOfferService bookingOfferService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{bookingOfferId}")
    public ResponseEntity<BookingOffer> getBookingOfferById(@PathVariable ObjectId bookingOfferId) {
        BookingOffer bookingOffer = bookingOfferService.getOfferById(bookingOfferId);
        return ResponseEntity.ok(bookingOffer);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingOffer>> getAllBookingOffers() {
        List<BookingOffer> bookingOffers = bookingOfferService.getAllBookingOffers();
        return ResponseEntity.ok(bookingOffers);
    }

    @PostMapping("/accommodation")
    public ResponseEntity<AccommodationOffer> createAccommodationOffer(@RequestBody AccommodationOffer accommodationOffer) {
        AccommodationOffer createdOffer = bookingOfferService.createAccommodationOffer(accommodationOffer);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

    @PostMapping("/hotel")
    public ResponseEntity<HotelOffer> createHotelOffer(@RequestBody HotelOffer hotelOffer) {
        HotelOffer createdOffer = bookingOfferService.createHotelOffer(hotelOffer);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

    @PostMapping("/flight")
    public ResponseEntity<FlightOffer> createFlightOffer(@RequestBody FlightOffer flightOffer) {
        FlightOffer createdOffer = bookingOfferService.createFlightOffer(flightOffer);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

    @PostMapping("/carRental")
    public ResponseEntity<CarRentalOffer> createCarRentalOffer(@RequestBody CarRentalOffer carRentalOffer) {
        CarRentalOffer createdOffer = bookingOfferService.createCarRentalOffer(carRentalOffer);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

}
