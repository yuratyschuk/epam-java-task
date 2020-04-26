package com.demo.servlet;

import com.demo.model.Places;
import com.demo.model.Route;
import com.demo.model.Trip;
import com.demo.service.PlaceService;
import com.demo.service.RouteService;
import com.demo.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TripServlet extends HttpServlet {

    private final TripService tripService;

    private final PlaceService placeService;

    private final RouteService routeService;

    private final String SEARCH_PAGE = "jsp/trip/searchTrip.jsp";

    private final String DETAILS_PAGE = "jsp/trip/tripDetails.jsp";

    private final String ADD_TRIP = "jsp/trip/trip.jsp";

    private String forward;

    private final static Logger logger = LogManager.getLogger();

    public TripServlet() {
        tripService = new TripService();
        placeService = new PlaceService();
        routeService = new RouteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("search")) {
            forward = SEARCH_PAGE;
            List<Places> placesList = placeService.getAll();

            request.setAttribute("placesList", placesList);

        } else if (action.equalsIgnoreCase("details")) {
            forward = DETAILS_PAGE;
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            Trip trip = tripService.getById(tripId);
            request.setAttribute("trip", trip);

        } else if(action.equalsIgnoreCase("tripAdd")) {
            forward = ADD_TRIP;
        } else if(action.equalsIgnoreCase("tripEdit")) {
            forward = ADD_TRIP;
            int tripId = Integer.parseInt(request.getParameter("tripId"));

            request.setAttribute("trip", tripService.getById(tripId));

        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer departurePlace = Integer.parseInt(request.getParameter("from"));
        Integer arrivalPlace = Integer.parseInt(request.getParameter("to"));

        Route route = routeService.getByDeparturePlaceIdAndArrivalPlaceId(departurePlace, arrivalPlace);

        List<Trip> tripList = tripService.getByRouteId(route.getId());

        request.setAttribute("tripList", tripList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/trip/searchResults.jsp");
        requestDispatcher.forward(request, response);

    }
}
