import com.chikli.demo.Flight;
import com.chikli.demo.FlightRepository;
import com.chikli.demo.FlightService;
import com.chikli.demo.ReservationCommand;
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
        public ReservationCommand reservationCommand() { return new ReservationCommand(); }
    }

    @MockBean
    private FlightRepository flightRepository;

    @Autowired
    FlightService flightService;

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
    public void getFlightsNotNull() {
        when(flightRepository.findAll()).thenReturn(new ArrayList());
        assertThat(flightService.getAllFlights()).isNotNull();
    }

    @Test
    public void getFlightsNotEmpty() {
        ArrayList flights = new ArrayList();
        flights.add(new Flight());
        when(flightRepository.findAll()).thenReturn(flights);
        assertThat(flightService.getAllFlights()).isNotEmpty();
    }

    @Test
    public void getFlightsWithSeatsAvailable() {
        ArrayList flights = new ArrayList();
        Flight f1 = new Flight();
        f1.setSeatsAvailable(10);
        flights.add(f1);
        when(flightRepository.findFlightsWithSeatsAvailable()).thenReturn(flights);
        List<Flight> availableFlights = flightService.getFlightsWithSeatsAvailable();
        assertThat(availableFlights).isNotEmpty();
        assertThat(availableFlights.get(0).getSeatsAvailable()).isGreaterThan(0);
    }

    @Test
    public void getFlightsWithNoSeatsAvailable() {
        ArrayList flights = new ArrayList();
        when(flightRepository.findFlightsWithSeatsAvailable()).thenReturn(new ArrayList<>());
        List<Flight> availableFlights = flightService.getFlightsWithSeatsAvailable();
        assertThat(availableFlights).isEmpty();
    }

    @Test
    public void addFlightTest() {
        Flight f = new Flight("PKB", "CMH", 400.00, "2019-05-02 15:00", "2019-05-02 18:00");
        when(flightRepository.save(f)).thenReturn(f);
        String output = reservationCommand.addFlight("PKB", "CMH", 400.00, "2019-05-02 15:00", "2019-05-02 18:00");
        assertEquals("Flight added!\n" + f.toString(), output);
    }

    Flight f1 = new Flight("PKB", "CMH", 400.00, "2005-05-02 13:00", "2005-05-02 16:00");
    @Test
    public void findFlight() {
        List<Flight> flights = new ArrayList<Flight>();
        flights.add(f1);
        when(flightRepository.findFlightByAiport("PKB", "")).thenReturn(flights);
        String output = reservationCommand.findFlight("PKB", "");
        String expectedOut = "\nFlight Details:\n\nPKB -> CMH - Price: 400.0\nDeparture Date/Time: 2005-05-02 01:00 PM\nArrival Date/Time: 2005-05-02 04:00 PM\r\n\n*********************\r\n\nFlights Found: 1\r\n";
        assertEquals(expectedOut, outContent.toString());
    }

}
