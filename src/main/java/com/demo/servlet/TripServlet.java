package com.demo.servlet;

import com.demo.dao.impl.StopRouteDaoImpl;
import com.demo.model.*;
import com.demo.model.utils.TrainType;
import com.demo.service.PlaceService;
import com.demo.service.RouteService;
import com.demo.service.TrainService;
import com.demo.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TripServlet extends HttpServlet {

    private final TripService tripService;

    private final PlaceService placeService;

    private final RouteService routeService;

    private final TrainService trainService;

    private final String SEARCH_PAGE = "jsp/trip/searchTrip.jsp";

    private final String DETAILS_PAGE = "jsp/trip/tripDetails.jsp";

    private final String ADD_TRIP = "jsp/trip/trip.jsp";

    private final String SEARCH_RESULTS = "jsp/trip/searchResults.jsp";

    private final String TRIP_LIST = "jsp/trip/tripList.jsp";

    private final StopRouteDaoImpl stopRouteDao;

    private String forward;

    private String action;

    private final static Logger logger = LogManager.getLogger();

    public TripServlet() {
        tripService = new TripService();
        placeService = new PlaceService();
        routeService = new RouteService();
        trainService = new TrainService();
        stopRouteDao = new StopRouteDaoImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action = request.getParameter("action");

        if (action.equalsIgnoreCase("search")) {
            forward = SEARCH_PAGE;
            List<Places> placesList = placeService.getAll();

            request.setAttribute("placesList", placesList);

        } else if (action.equalsIgnoreCase("details")) {
            forward = DETAILS_PAGE;
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            Trip trip = tripService.getById(tripId);
            logger.info(trip.getStopSet());
            request.setAttribute("trip", trip);

        } else if (action.equalsIgnoreCase("tripAdd")) {
            forward = ADD_TRIP;

            request.setAttribute("placesList", placeService.getAll());
            request.setAttribute("trainList", trainService.getAll());

        } else if (action.equalsIgnoreCase("tripUpdate")) {
            forward = ADD_TRIP;
            int tripId = Integer.parseInt(request.getParameter("tripId"));

            request.setAttribute("trip", tripService.getById(tripId));
            request.setAttribute("placesList", placeService.getAll());
            request.setAttribute("trainList", trainService.getAll());
        } else if (action.equalsIgnoreCase("tripDelete")) {
            String tripRedirect = request.getContextPath() + "/trip?action=tripList";
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            tripService.deleteById(tripId);

            response.sendRedirect(tripRedirect);
            return;
        } else if(action.equalsIgnoreCase("tripList")){
            forward = TRIP_LIST;
            request.setAttribute("tripList", tripService.getAll());
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        action = request.getParameter("action");

        if (action.equalsIgnoreCase("searchTrip")) {
            forward = SEARCH_RESULTS;

            int departurePlace = Integer.parseInt(request.getParameter("from"));
            int arrivalPlace = Integer.parseInt(request.getParameter("to"));
            Route route = routeService.getByDeparturePlaceIdAndArrivalPlaceId(departurePlace, arrivalPlace);

            List<Trip> tripList = tripService.getByRouteId(route.getId());
            request.setAttribute("tripList", tripList);
        } else if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("edit")) {

            String tripRedirect = request.getContextPath() + "/trip?action=tripList";
            createOrEditTripPost(request, response);

            response.sendRedirect(tripRedirect);
            return;
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(SEARCH_RESULTS);
        requestDispatcher.forward(request, response);
    }

    private void createOrEditTripPost(HttpServletRequest request, HttpServletResponse response) {

        String datePattern = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(datePattern);

        try {
            Trip trip = new Trip();
            String tripId = request.getParameter("tripId");
            Date departureTime = dateFormat.parse(request.getParameter("departureTime"));
            Date arrivalTime = dateFormat.parse(request.getParameter("arrivalTime"));
            int numberOfCarriages = Integer.parseInt(request.getParameter("numberOfCarriages"));
            int trainId = Integer.parseInt(request.getParameter("trainId"));
            String trainType = request.getParameter("trainType");

            if(TrainType.MULTI.toString().equals(trainType) || TrainType.PASSENGER.toString().equals(trainType)) {
                BigDecimal price = BigDecimal.valueOf(Long.parseLong(request.getParameter("price")));
                int numberOfPlaces = Integer.parseInt(request.getParameter("numberOfPlaces"));
                trip.setTicketPrice(price);
                trip.setNumberOfPlaces(numberOfPlaces);
            }

            int departureId = Integer.parseInt(request.getParameter("departurePlaceId"));
            int arrivalId = Integer.parseInt(request.getParameter("arrivalPlaceId"));
            Route route = routeService.getByDeparturePlaceIdAndArrivalPlaceId(departureId, arrivalId);

            if (route == null) {
                route = new Route();

                Places departurePlace = new Places();
                departurePlace.setId(departureId);
                route.setDeparturePlace(departurePlace);

                Places arrivalPlace = new Places();
                arrivalPlace.setId(arrivalId);
                route.setArrivalPlace(arrivalPlace);

                route = routeService.save(route);

            }
            trip.setDepartureTime(departureTime);
            trip.setArrivalTime(arrivalTime);
            trip.setRoute(route);

            Train train = new Train();
            train.setId(trainId);
            trip.setTrain(train);
            trip.setNumberOfCarriages(numberOfCarriages);
            int tripIdInt;
            if (tripId == null || tripId.isEmpty()) {
              trip =   tripService.save(trip);
              tripIdInt = trip.getId();
            } else {
                tripIdInt = Integer.parseInt(tripId);
                trip.setId(tripIdInt);
                tripService.update(trip);
            }
            String[] stopIdString = request.getParameterValues("stopPlaceId");
            for(String s : stopIdString) {
                int stopId = Integer.parseInt(s);
                stopRouteDao.save(tripIdInt, stopId);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
    }
}
