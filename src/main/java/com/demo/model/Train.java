package com.demo.model;

import com.demo.model.utils.TrainType;

public class Train {

    private Integer id;

    private String trainName;

    private String trainNumber;

    private int maxNumberOfCarriages;

    private TrainType trainType;

    public Train() {
    }

    public Train(Integer id, String trainName, String trainNumber, int maxNumberOfCarriages, TrainType trainType) {
        this.id = id;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.maxNumberOfCarriages = maxNumberOfCarriages;
        this.trainType = trainType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getMaxNumberOfCarriages() {
        return maxNumberOfCarriages;
    }

    public void setMaxNumberOfCarriages(int maxNumberOfCarriages) {
        this.maxNumberOfCarriages = maxNumberOfCarriages;
    }

    public TrainType getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", trainName='" + trainName + '\'' +
                ", trainNumber='" + trainNumber + '\'' +
                ", maxNumberOfCarriages=" + maxNumberOfCarriages +
                ", trainType=" + trainType +
                '}';
    }
}
