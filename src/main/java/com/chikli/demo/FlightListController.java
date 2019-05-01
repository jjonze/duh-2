package com.chikli.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class FlightListController {

    @Autowired
    FlightService flightService;

    @GetMapping("/all")
    private List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/available")
    private List<Flight> getAvailableFlights() {
        return flightService.getFlightsWithSeatsAvailable();
    }

    @PostMapping("/flights")
    private long saveFlight(@RequestBody Flight flight) {
        flightService.saveOrUpdate(flight);
        return flight.getId();
    }
}
