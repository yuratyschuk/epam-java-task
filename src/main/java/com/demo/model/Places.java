package com.demo.model;

public class Places {

    private Integer id;

    private String placeName;

    public Places() {
    }

    public Places(Integer id, String placeName) {
        this.id = id;
        this.placeName = placeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return "Places{" +
                "id=" + id +
                ", placeName='" + placeName + '\'' +
                '}';
    }
}
