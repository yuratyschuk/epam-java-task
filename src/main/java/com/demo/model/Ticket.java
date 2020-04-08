package com.demo.model;

import java.sql.Date;
import java.time.LocalDate;

public class Ticket {

    private Integer id;

    private Trip trip;

    private Date timeWhenTicketWasBought;

    public Ticket() {
    }

    public Ticket(Integer id, Trip trip, Date timeWhenTicketWasBought) {
        this.id = id;
        this.trip = trip;
        this.timeWhenTicketWasBought = timeWhenTicketWasBought;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Date getTimeWhenTicketWasBought() {
        return timeWhenTicketWasBought;
    }

    public void setTimeWhenTicketWasBought(Date timeWhenTicketWasBought) {
        this.timeWhenTicketWasBought = timeWhenTicketWasBought;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "id=" + id +
                ", trip=" + trip +
                ", timeWhenTicketWasBought=" + timeWhenTicketWasBought +
                '}';
    }
}
