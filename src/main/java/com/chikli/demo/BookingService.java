package com.chikli.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    FlightRepository flightRepository;

    public Booking saveOrUpdate(Booking booking) {

        Flight f = flightRepository.findById(booking.getFlightId()).get();
        bookingRepository.save(booking);
        f.setSeatsAvailable(f.getSeatsAvailable() - 1);
        flightRepository.save(f);
        booking.setFlight(f);

        return booking;
    }

    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookingRepository.findAll().forEach(booking-> bookings.add(booking));
        return bookings;
    }
}
