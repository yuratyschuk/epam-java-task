package com.demo.model;

import java.math.BigDecimal;
import java.util.Date;


public class Trip {

    private Integer id;

    private Date departureTime;

    private Date arrivalTime;

    private Route route;

    private BigDecimal ticketPrice;

    private Train train;

    private int numberOfCarriages;

    private int numberOfPlaces;

    public Trip() {
    }

    public Trip(Integer id, Date departureTime, Date arrivalTime, Route route, BigDecimal ticketPrice, Train train, int numberOfCarriages) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.route = route;
        this.ticketPrice = ticketPrice;
        this.train = train;
        this.numberOfCarriages = numberOfCarriages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public int getNumberOfCarriages() {
        return numberOfCarriages;
    }

    public void setNumberOfCarriages(int numberOfCarriages) {
        this.numberOfCarriages = numberOfCarriages;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
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
                ", numberOfCarriages=" + numberOfCarriages +
                '}';
    }
}
