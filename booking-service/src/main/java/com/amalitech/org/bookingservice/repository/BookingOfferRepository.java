package com.amalitech.org.bookingservice.repository;

import com.amalitech.org.bookingservice.entity.BookingOffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingOfferRepository extends MongoRepository<BookingOffer, String> {

}