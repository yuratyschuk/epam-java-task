package com.demo.servlet;

import com.demo.model.Trip;
import com.demo.service.TripService;

import javax.servlet.http.HttpServlet;

public class TripServlet extends HttpServlet {

    TripService tripService;



    public TripServlet() {
         tripService = new TripService();
    }


}
