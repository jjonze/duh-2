package com.chikli.demo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.seatsAvailable > 0")
    Collection<Flight> findFlightsWithSeatsAvailable();

    @Query("SELECT f FROM Flight f WHERE f.fromAirport LIKE CONCAT('%',:fromAirport,'%') AND f.toAirport LIKE CONCAT('%',:toAirport,'%')")
    List<Flight> findFlightByAiport(@Param("fromAirport") String fromAirport, @Param("toAirport") String toAirport);
}
