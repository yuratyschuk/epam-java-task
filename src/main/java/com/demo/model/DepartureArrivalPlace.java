package com.demo.model;

public class DepartureArrivalPlace {

    private Long id;

    private String placeName;

    public DepartureArrivalPlace() {
    }

    public DepartureArrivalPlace(Long id, String placeName) {
        this.id = id;
        this.placeName = placeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Override
    public String toString() {
        return "DepartureArrivalPlace{" +
                "id=" + id +
                ", placeName='" + placeName + '\'' +
                '}';
    }
}
