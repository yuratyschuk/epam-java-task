package com.demo;

import com.demo.model.*;
import com.demo.model.utils.TrainType;
import com.demo.service.*;
import com.demo.servlet.UserServlet;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        TripService tripService = new TripService();
        List<Trip> trips = tripService.getAll();

        for (Trip trip : trips) {
            System.out.println(trip.getId() + " " + trip.getStopSet());
        }
    }
}
