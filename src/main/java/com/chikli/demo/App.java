package com.chikli.demo;
//test
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    FlightService flightSerivce;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {
        List<Flight> flights = getBaseData();
        for (int i = 0; i < flights.size(); i++) {
            flightSerivce.saveOrUpdate(flights.get(i));
        }
    }

    public List<Flight> getBaseData() {
        List<Flight> flights = new ArrayList<Flight>();

        Flight f1 = new Flight();
        f1.setFromAirport("PKB");
        f1.setToAirport("CMH");
        f1.setSeatsAvailable(10);

        Flight f2 = new Flight();
        f2.setFromAirport("PKB");
        f2.setToAirport("RNO");
        f2.setSeatsAvailable(0);

        flights.add(f1);
        flights.add(f2);

        return flights;
    }

}