import com.chikli.demo.Flight;
import com.chikli.demo.FlightRepository;
import com.chikli.demo.FlightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FlightTests {

    @TestConfiguration
    static class FlightServiceImplTestContextConfiguration {

        @Bean
        public FlightService flightService() {
            return new FlightService();
        }
    }

    @MockBean
    private FlightRepository flightRepository;

    @Autowired
    FlightService flightService;

    private List<Flight> flights = new ArrayList<>();

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

}
