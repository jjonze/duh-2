import com.chikli.demo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookingTests {

    private MockMvc mockMvc;

    @TestConfiguration
    static class BookingServiceImplTestContextConfiguration {

        @Bean
        public BookingService bookingService() {
            return new BookingService();
        }

        @Bean
        public BookingController bookingController() { return new BookingController(); }
    }

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    BookingService bookingService;

    @Autowired
    FlightListController flightListController;

    @Test
    public void saveBookTest() throws Exception {
        mockMvc.perform(get("/savebooking"));
        //bookingController.saveBooking();
    }

}
