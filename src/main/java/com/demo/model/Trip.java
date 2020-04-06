package com.demo.model;

import java.time.LocalDate;

public class Trip {

    private Long id;

    private LocalDate departureTime;

    private LocalDate arrivalTime;

    private Route route;

    private int ticketPrice;

    private Train train;

    public Trip() {
    }

    public Trip(Long id, LocalDate departureTime, LocalDate arrivalTime, Route route, int ticketPrice, Train train) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.route = route;
        this.ticketPrice = ticketPrice;
        this.train = train;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDate arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", route=" + route +
                ", ticketPrice=" + ticketPrice +
                ", train=" + train +
                '}';
    }
}
