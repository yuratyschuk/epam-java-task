package com.demo.model;

import java.sql.Date;

public class Worker {

    private Integer id;

    private String firstName;

    private String lastName;

    private Position position;

    private int workingExperience;

    private Date hireDate;

    public Worker() {
    }

    public Worker(String firstName, String lastName, Position position, int workingExperience, Date hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.workingExperience = workingExperience;
        this.hireDate = hireDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getWorkingExperience() {
        return workingExperience;
    }

    public void setWorkingExperience(int workingExperience) {
        this.workingExperience = workingExperience;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Workers{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position=" + position +
                ", workingExperience=" + workingExperience +
                ", hireDate=" + hireDate +
                '}';
    }
}
