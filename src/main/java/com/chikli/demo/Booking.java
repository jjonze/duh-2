//janetta, sharice, amber, amy
package com.chikli.demo;

import javax.persistence.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long flightId;

    private String name;

    @Transient
    private Flight flight;

    public Booking() {}

    public Booking(long flightId, String name) {
        this.flightId = flightId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Booking:\n\n");
        sb.append("Name: " + this.getName());
        return sb.toString();
    }
}
