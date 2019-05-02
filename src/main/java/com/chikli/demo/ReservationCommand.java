package com.chikli.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.PostConstruct;
import java.awt.print.Book;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ShellComponent
public class ReservationCommand {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    BookingRepository bookingRepository;

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
    public String listFlights() {
        System.out.print("\nAvailable Flights:\n");
        Iterable<Flight> flights = flightRepository.findAll();
        for (Flight flight : flights) {
            System.out.print(flight.getId() + ": " + flight.getFromAirport() + " > " + flight.getToAirport() + "\n");
        }
        System.out.print("\nTotal Flights: " + ((Collection<?>) flights).size() + "\n");

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
            System.out.print(flight.toString());
            System.out.print("\n\n*********************\n");
        }
        System.out.print("\nFlights Found: " + flights.size() + "\n");

        return "";
    }

    @ShellMethod(value="add flight")
    public String addFlight(String fromAirport, String toAirport, double price, @ShellOption(help="format: \"2000-01-31 15:00\"") String departureDateTime, @ShellOption(help="format: \"2000-01-31 15:00\"") String arrivalDateTime) {
        Flight f = new Flight(fromAirport, toAirport, price, departureDateTime, arrivalDateTime);
        flightRepository.save(f);

        return "Flight added!\n" + f.toString();
    }

    @ShellMethod(value="book flight")
    public String bookFlight(long flightId, String name) {
        Booking b = new Booking(flightId, name);
        Flight f = flightRepository.findById(flightId).get();
        bookingRepository.save(b);

        return "Flight Booked!\n" + f.toString();
    }

    @ShellMethod(value="list bookings")
    public void listBookings() {
        Iterable<Booking> bookings = bookingRepository.findAll();
        System.out.print("\nFlight Reservations:\n\n");
        for (Booking booking:bookings) {
            System.out.print(booking.toString());
            System.out.print("\n");
            System.out.print(flightRepository.findById(booking.getFlightId()).get().toString());
            System.out.print("\n\n*********************\n\n");
        }
        System.out.print("Total Bookings: " + ((Collection<?>)bookings).size() + "\n");
    }
}