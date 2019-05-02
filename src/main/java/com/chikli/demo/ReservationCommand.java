package com.chikli.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ShellComponent
public class ReservationCommand {

    @Autowired
    FlightRepository flightRepository;

    private boolean dataLoaded = false;

    @PostConstruct
    public void init() {
        if (!this.dataLoaded) {
            flightRepository.save(new Flight("PKB", "CMH", 400.00, "2019-05-02 10:00", "2019-05-02 13:00"));
            flightRepository.save(new Flight("PKB", "RNO", 800.00, "2019-05-02 10:00", "2019-05-02 17:00"));
            this.dataLoaded = true;
        }
    }

    @ShellMethod(value = "list all flights")
    public String listFlights() throws ExecutionException, InterruptedException {
        System.out.println();
        System.out.println("Available Flights:");
        System.out.println();
        Iterable<Flight> flights = flightRepository.findAll();
        for (Flight flight : flights) {
            System.out.println(flight.getId() + ": " + flight.getFromAirport() + " > " + flight.getToAirport());
        }
        System.out.println();
        System.out.println("Total Flights: " + ((Collection<?>) flights).size());

        return "";
    }

    @ShellMethod(value="get flight details")
    public String getFlightDetails(long flightId) {
        Flight flight = flightRepository.findById(flightId).get();

        return flight.toString();
    }

    @ShellMethod(value="find flight")
    public String findFlight(String fromAirport, @ShellOption(defaultValue = "") String toAirport) {
        List<Flight> flights = flightRepository.findFlightByAiport(fromAirport, toAirport);
        for (Flight flight: flights) {
            System.out.println(flight.toString());
            System.out.println("\n*********************");
        }
        System.out.println("\nFlights Found: " + flights.size());

        return "";
    }

    @ShellMethod(value="add flight")
    public String addFlight(String fromAirport, String toAirport, double price, @ShellOption(help="format: \"2000-01-31 15:00\"") String departureDateTime, @ShellOption(help="format: \"2000-01-31 15:00\"") String arrivalDateTime) {
        Flight f = new Flight(fromAirport, toAirport, price, departureDateTime, arrivalDateTime);
        flightRepository.save(f);

        return "Flight added!\n" + f.toString();
    }
}