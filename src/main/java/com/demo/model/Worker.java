package com.demo.model;

import com.demo.model.utils.Person;

import java.sql.Date;

public class Worker extends Person {

    private Integer id;


    private Position position;

    private int workingExperience;

    private Date hireDate;

    public Worker() {
    }

    public Worker(Position position, int workingExperience, Date hireDate) {

        this.position = position;
        this.workingExperience = workingExperience;
        this.hireDate = hireDate;
    }

    public Worker(int workingExperience, Date hireDate) {
        this.workingExperience = workingExperience;
        this.hireDate = hireDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
                ", position=" + position +
                ", workingExperience=" + workingExperience +
                ", hireDate=" + hireDate +
                '}';
    }
}
