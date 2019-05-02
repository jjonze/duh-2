import com.chikli.demo.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FlightTests {

    @TestConfiguration
    static class FlightServiceImplTestContextConfiguration {

        @Bean
        public FlightService flightService() {
            return new FlightService();
        }

        @Bean
        public BookingService bookingService() {
            return new BookingService();
        }

        @Bean
        public ReservationCommand reservationCommand() { return new ReservationCommand(); }
    }

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    FlightService flightService;

    @Autowired
    BookingService bookingService;

    @Autowired
    ReservationCommand reservationCommand;

    private List<Flight> flights = new ArrayList<>();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void addFlightTest() {
        Flight f = new Flight("PKB", "CMH", 400.00, "2019-05-02 15:00", "2019-05-02 18:00", 100, 85);
        when(flightRepository.save(f)).thenReturn(f);
        String output = reservationCommand.addFlight("PKB", "CMH", 400.00, "2019-05-02 15:00", "2019-05-02 18:00", 100, 85);
        assertEquals("Flight added!\n" + f.toString(), output);
    }

    private Flight f1 = new Flight("PKB", "CMH", 400.00, "2005-05-02 13:00", "2005-05-02 16:00", 100, 85);

    @Test
    public void findFlightTest() {
        List<Flight> flights = new ArrayList<Flight>();
        flights.add(f1);
        when(flightRepository.findFlightByAiport("PKB", "")).thenReturn(flights);
        String output = reservationCommand.findFlight("PKB", "");
        String expectedOut = "\nFlight Details:\n\nPKB -> CMH - Price: 400.0\nDeparture Date/Time: 2005-05-02 01:00 PM\nArrival Date/Time: 2005-05-02 04:00 PM\n\n*********************\n\nFlights Found: 1\n";
        assertEquals(expectedOut, outContent.toString());
    }

    @Test
    public void getFlightDetailsTest() {
        when(flightRepository.findById(1l)).thenReturn(Optional.of(f1));
        String output = reservationCommand.getFlightDetails(1);
        String expectedOut = f1.toString();
        assertEquals(expectedOut, output);
    }

    @Test
    public void listFlightsTest() {
        List<Flight> flights = new ArrayList<Flight>();
        flights.add(f1);
        when(flightRepository.findFlightsWithSeatsAvailable()).thenReturn(flights);
        reservationCommand.listFlights();
        String expected = "\nAvailable Flights:\n\n" + f1.getId() + ": " + f1.getFromAirport() + " > " + f1.getToAirport() + "\nSeats Available: " + f1.getSeatsAvailable() + "\n\nTotal Flights: 1\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void bookFlightTest() {
        f1.setId(1);
        Booking b = new Booking(1, "Brad");
        int seatsAvailable = f1.getSeatsAvailable();
        when(flightRepository.findById(1l)).thenReturn(Optional.of(f1));
        when(bookingRepository.save(b)).thenReturn(b);
        String output = reservationCommand.bookFlight(1, "Brad");
        assertEquals("Flight Booked!\n" + f1.toString(), output);
        assertEquals(f1.getSeatsAvailable(), seatsAvailable-1);
    }

    @Test
    public void saveOrUpdateBookingTest() {
        f1.setId(1);
        Booking b = new Booking(1, "Brad");
        int seatsAvailable = f1.getSeatsAvailable();
        when(flightRepository.findById(1l)).thenReturn(Optional.of(f1));
        when(bookingRepository.save(b)).thenReturn(b);
        bookingService.saveOrUpdate(b);
        assertEquals(f1.getSeatsAvailable(), seatsAvailable-1);
    }

    @Test
    public void listBookingsTest() {
        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(new Booking(1, "Brad"));
        when(bookingRepository.findAll()).thenReturn(bookings);
        when(flightRepository.findById(1l)).thenReturn(Optional.of(f1));
        reservationCommand.listBookings();
        String expected = "\nFlight Reservations:\n\n" + bookings.get(0).toString() + "\n" + f1.toString() + "\n\n*********************\n\nTotal Bookings: 1\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void departureTimeInvalidTest() {
        String output = reservationCommand.addFlight("PKB", "CMH", 400.00, "10:00AM", "11:00AM", 85, 85);
        assertEquals(output, "Invalid Date Format");
    }

    @Test
    public void arrivalTimeInvalidTest() {
        String output = reservationCommand.addFlight("PKB", "CMH", 400.00, "2019-05-02 13:00", "11:00AM", 85, 85);
        assertEquals(output, "Invalid Date Format");
    }

}
