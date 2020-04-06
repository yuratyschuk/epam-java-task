package com.demo.model;

import java.util.Set;

public class Route {

    private Long id;

    private DepartureArrivalPlace departurePlace;

    private DepartureArrivalPlace arrivalPlace;

    private Set<Stop> stopsList;

    public Route() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DepartureArrivalPlace getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(DepartureArrivalPlace departurePlace) {
        this.departurePlace = departurePlace;
    }

    public DepartureArrivalPlace getArrivalPlace() {
        return arrivalPlace;
    }

    public void setArrivalPlace(DepartureArrivalPlace arrivalPlace) {
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
