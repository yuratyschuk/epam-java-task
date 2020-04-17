package com.demo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Position {

    private Integer id;

    private String jobName;

    private BigDecimal salary;

    private List<Worker> workerList;

    public Position() {
        this.workerList = new ArrayList<>();
    }

    public Position(String jobName, BigDecimal salary, List<Worker> workerList) {

        this.jobName = jobName;
        this.salary = salary;
        this.workerList = workerList;
    }

    public Position(Integer id, String jobName, BigDecimal salary) {
        this.id = id;
        this.jobName = jobName;
        this.salary = salary;
        this.workerList = new ArrayList<>();
    }

    public Position(String jobName, BigDecimal salary) {
        this.jobName = jobName;
        this.salary = salary;
    }

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(List<Worker> workerList) {
        this.workerList.addAll(workerList);
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


    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", salary=" + salary +
                ", workerList=" + workerList +
                '}';
    }
}
