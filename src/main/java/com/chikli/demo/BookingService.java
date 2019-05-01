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

    public void saveOrUpdate(Booking booking) {
        bookingRepository.save(booking);
    }

    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookingRepository.findAll().forEach(booking-> bookings.add(booking));
        return bookings;
    }
}
