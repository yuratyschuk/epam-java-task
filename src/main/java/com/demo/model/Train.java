package com.demo.model;

public class Train {

    private Long id;

    private String trainName;

    private String trainNumber;

    private int countOfRailwayCarriage;

    private int countOfPlaces;


    public Train() {
    }

    public Train(Long id, String trainName, String trainNumber, int countOfRailwayCarriage, int countOfPlaces) {
        this.id = id;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.countOfRailwayCarriage = countOfRailwayCarriage;
        this.countOfPlaces = countOfPlaces;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getCountOfRailwayCarriage() {
        return countOfRailwayCarriage;
    }

    public void setCountOfRailwayCarriage(int countOfRailwayCarriage) {
        this.countOfRailwayCarriage = countOfRailwayCarriage;
    }

    public int getCountOfPlaces() {
        return countOfPlaces;
    }

    public void setCountOfPlaces(int countOfPlaces) {
        this.countOfPlaces = countOfPlaces;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", trainName='" + trainName + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", countOfRailwayCarriage=" + countOfRailwayCarriage +
                ", countOfPlaces=" + countOfPlaces +
                '}';
    }
}
