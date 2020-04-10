package com.demo;

import com.demo.model.Position;
import com.demo.model.Trip;
import com.demo.model.Worker;
import com.demo.service.PositionService;
import com.demo.service.TripService;
import com.demo.service.WorkerService;

import java.sql.Date;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        PositionService positionService = new PositionService();
        System.out.println(positionService.getAll());
    }

}
