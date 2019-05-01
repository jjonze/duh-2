package com.chikli.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public void saveOrUpdate(Booking booking) {
        bookingRepository.save(booking);
    }
}
