package com.chikli.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        flightRepository.findAll().forEach(flight -> flights.add(flight));
        return flights;
    }

    public void saveOrUpdate(Flight flight) {
        flightRepository.save(flight);
    }
}
