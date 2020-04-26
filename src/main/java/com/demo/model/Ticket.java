package com.demo.model;

import java.util.Date;

public class Ticket {

    private Integer id;

    private Trip trip;

    private Date timeWhenTicketWasBought;

    private User client;

    public Ticket() {
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

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
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
