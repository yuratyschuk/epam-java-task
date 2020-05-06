package com.demo.model;

import java.util.Date;

public class Ticket {

    private Integer id;

    private Trip trip;

    private Date timeWhenTicketWasBought;

    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
