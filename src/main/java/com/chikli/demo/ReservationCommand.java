package com.chikli.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.PostConstruct;
import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ShellComponent
public class ReservationCommand {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingService bookingService;

    private boolean dataLoaded = false;

    @PostConstruct
    public void init() {
        if (!this.dataLoaded) {
            flightRepository.save(new Flight("PKB", "CMH", 400.00, "2019-05-02 10:00", "2019-05-02 13:00", 100, 85));
            flightRepository.save(new Flight("PKB", "RNO", 800.00, "2019-05-02 10:00", "2019-05-02 17:00", 85, 0));
            this.dataLoaded = true;
        }
    }

    @ShellMethod(value = "list all flights")
    public String listFlights() {
        System.out.print("\nAvailable Flights:\n\n");
        Iterable<Flight> flights = flightRepository.findFlightsWithSeatsAvailable();
        for (Flight flight : flights) {
            System.out.print(flight.getId() + ": " + flight.getFromAirport() + " > " + flight.getToAirport() + "\nSeats Available: " + flight.getSeatsAvailable()+"\n\n");
        }
        System.out.print("Total Flights: " + ((Collection<?>) flights).size() + "\n");

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
    public String addFlight(String fromAirport, String toAirport, double price, @ShellOption(help="format: \"2000-01-31 15:00\"") String departureDateTime, @ShellOption(help="format: \"2000-01-31 15:00\"") String arrivalDateTime, int seatCount, int seatsAvailable) {
        try {
            new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(departureDateTime);
            new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(arrivalDateTime);
        }
        catch (ParseException pe) {
            return "Invalid Date Format";
        }
        Flight f = new Flight(fromAirport, toAirport, price, departureDateTime, arrivalDateTime, seatCount, seatsAvailable);
        flightRepository.save(f);

        return "Flight added!\n" + f.toString();
    }

    @ShellMethod(value="book flight")
    public String bookFlight(long flightId, String name) {
        Booking b = new Booking(flightId, name);
        b = bookingService.saveOrUpdate(b);

        return "Flight Booked!\n" + b.getFlight().toString();
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