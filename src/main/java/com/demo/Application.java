package com.demo;

import com.demo.model.Position;
import com.demo.model.Worker;
import com.demo.service.DAO;
import com.demo.service.impl.WorkerDaoImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        DAO workerDao = new WorkerDaoImpl();
        Date date = new Date(2017, 11, 22);
        Worker worker = new Worker();
        worker.setId(2);
        worker.setFirstName("Yura");
        worker.setLastName("Tyschuk");
        worker.setWorkingExperience(2);
        worker.setPosition(new Position());
        worker.setHireDate(date);
        workerDao.update(worker);
    }
}
