package com.demo.model;

import java.time.LocalDate;

public class Tickets {

    private Long id;

    private Trip trip;

    private LocalDate timeWhenTicketWasBought;

    public Tickets() {
    }

    public Tickets(Long id, Trip trip, LocalDate timeWhenTicketWasBought) {
        this.id = id;
        this.trip = trip;
        this.timeWhenTicketWasBought = timeWhenTicketWasBought;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public LocalDate getTimeWhenTicketWasBought() {
        return timeWhenTicketWasBought;
    }

    public void setTimeWhenTicketWasBought(LocalDate timeWhenTicketWasBought) {
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
