package com.demo.model;

import java.util.Set;

public class Route {

    private Integer id;

    private Places departurePlace;

    private Places arrivalPlace;

    private Set<Stop> stopsList;

    public Route() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Places getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(Places departurePlace) {
        this.departurePlace = departurePlace;
    }

    public Places getArrivalPlace() {
        return arrivalPlace;
    }

    public void setArrivalPlace(Places arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public Set<Stop> getStopsList() {
        return stopsList;
    }

    public void setStopsList(Set<Stop> stopsList) {
        this.stopsList = stopsList;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", departurePlace=" + departurePlace +
                ", arrivalPlace=" + arrivalPlace +
                ", stopsList=" + stopsList +
                '}';
    }
}
