package com.demo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Position {

    private Integer id;

    private String jobName;

    private BigDecimal salary;

    private boolean active;

    public Position() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", salary=" + salary +
                ", active=" + active +
                '}';
    }
}
