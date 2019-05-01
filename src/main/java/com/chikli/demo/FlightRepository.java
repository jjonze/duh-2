package com.chikli.demo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource
public interface FlightRepository extends CrudRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.seatsAvailable > 0")
    Collection<Flight> findFlightsWithSeatsAvailable();

    @Query("SELECT f.fromAirport FROM Flight f WHERE f.fromAirport LIKE CONCAT('%',:fromAirport,'%')")
    List<String> findFromAirport(@Param("fromAirport") String fromAirport);
}
