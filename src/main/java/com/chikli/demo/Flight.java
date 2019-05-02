package com.chikli.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fromAirport;
    private String toAirport;

    private double price;

    private Date departureDateTime;
    private Date arrivalDateTime;

    private int seatCount;

    public Flight() { }

    public Flight(String fromAirport,
                  String toAirport,
                  double price,
                  String departureDateTime,
                  String arrivalDateTime,
                  int seatCount,
                  int seatsAvailable) {
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.price = price;
        this.seatCount = seatCount;
        this.seatsAvailable = seatsAvailable;

        this.setDepartureDateTime(departureDateTime);
        this.setArrivalDateTime(arrivalDateTime);
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    private int seatsAvailable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public String getDepartureDateTimeString() {
        return this.getDateTimeString(this.departureDateTime);
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = getDateTime(departureDateTime);
    }

    public Date getArrivalDateTime() {
        return arrivalDateTime;
    }

    public String getArrivalDateTimeString() {
        return this.getDateTimeString(this.arrivalDateTime);
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = getDateTime(arrivalDateTime);
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public Date getDateTime(String date) {
        Date dateTime = null;
        try {
            dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        }
        catch(ParseException pe) {
            pe.printStackTrace();
        }

        return dateTime;
    }
    public String getDateTimeString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(date);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\nFlight Details:\n\n");
        sb.append(this.getFromAirport());
        sb.append(" -> ");
        sb.append(this.getToAirport());
        sb.append(" - Price: " + this.getPrice());
        sb.append("\nDeparture Date/Time: ");
        sb.append(this.getDepartureDateTimeString());
        sb.append("\nArrival Date/Time: " + this.getArrivalDateTimeString());
        return  sb.toString();
    }
}
