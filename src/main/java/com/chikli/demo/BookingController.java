package com.chikli.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping("/test")
    public String test() {
        return "Testing";
    }

    @PostMapping("/save")
    public long saveBooking(@RequestBody Booking booking) {
        bookingService.saveOrUpdate(booking);
        return booking.getId();
    }
}
