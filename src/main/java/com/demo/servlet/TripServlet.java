package com.demo.servlet;

import com.demo.dao.impl.*;
import com.demo.dao.interfaces.PlacesDao;
import com.demo.model.Places;
import com.demo.model.Route;
import com.demo.model.Train;
import com.demo.model.Trip;
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
import java.util.Date;
import java.util.List;

public class TripServlet extends HttpServlet {

    private TripService tripService;

    private PlaceService placeService;

    private RouteService routeService;

    private TrainService trainService;

    private static final String SEARCH_PAGE = "jsp/trip/searchTrip.jsp";

    private static final String DETAILS_PAGE = "jsp/trip/tripDetails.jsp";

    private static final String ADD_TRIP = "jsp/trip/trip.jsp";

    private static final String SEARCH_RESULTS = "jsp/trip/searchResults.jsp";

    private static final String TRIP_LIST = "jsp/trip/tripList.jsp";


    private String forward;

    private String action;

    private final static Logger logger = LogManager.getLogger();

    private List<Trip> tripList;

    private List<Train> trainList;

    private List<Places> placesList;

    @Override
    public void init() throws ServletException {
        tripService = new TripService(new TripDaoImpl(), new StopDaoImpl());
        placeService = new PlaceService(new PlacesDaoImpl());
        routeService = new RouteService(new RouteDaoImpl());
        trainService = new TrainService(new TrainDaoImpl());

        tripList = tripService.getAll();
        trainList = trainService.getAll();
        placesList = placeService.getAll();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action = request.getParameter("action");

        if (action.equalsIgnoreCase("tripSearch")) {
            forward = SEARCH_PAGE;

            request.setAttribute("placesList", placesList);

        } else if (action.equalsIgnoreCase("tripDetails")) {
            forward = DETAILS_PAGE;
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            Trip trip = tripService.getById(tripId);
            request.setAttribute("trip", trip);

        } else if (action.equalsIgnoreCase("tripAdd")) {
            forward = ADD_TRIP;

            request.setAttribute("placesList", placesList);
            request.setAttribute("trainList", trainList);

        } else if (action.equalsIgnoreCase("tripUpdate")) {
            forward = ADD_TRIP;
            int tripId = Integer.parseInt(request.getParameter("tripId"));

            request.setAttribute("trip", tripService.getById(tripId));
            request.setAttribute("placesList", placesList);
            request.setAttribute("trainList", trainList);
        } else if (action.equalsIgnoreCase("tripDelete")) {
            String tripRedirect = request.getContextPath() + "/trip?action=tripList";
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            tripService.deleteById(tripId);

            response.sendRedirect(tripRedirect);
            return;
        } else if (action.equalsIgnoreCase("tripList")) {
            forward = TRIP_LIST;
            request.setAttribute("tripList", tripList);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        action = request.getParameter("action");

        if (action.equalsIgnoreCase("searchTrip")) {
            forward = SEARCH_RESULTS;

            searchTrip(request);
        } else if (action.equalsIgnoreCase("tripAdd") ||
                action.equalsIgnoreCase("tripUpdate")) {

            String tripRedirect = request.getContextPath() + "/trip?action=tripList";
            createOrEditTripPost(request, action);

            response.sendRedirect(tripRedirect);
            return;
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(SEARCH_RESULTS);
        requestDispatcher.forward(request, response);
    }

    private void searchTrip(HttpServletRequest request) {

        int departurePlace = Integer.parseInt(request.getParameter("from"));
        int arrivalPlace = Integer.parseInt(request.getParameter("to"));
        Route route = routeService.getByDeparturePlaceIdAndArrivalPlaceId(departurePlace, arrivalPlace);

        List<Trip> tripListByRouteId = tripService.getByRouteId(route.getId());
        request.setAttribute("tripList", tripListByRouteId);

    }

    private void createOrEditTripPost(HttpServletRequest request, String action) {

        String datePattern = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(datePattern);

        try {
            Trip trip = new Trip();
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            Date departureTime = dateFormat.parse(request.getParameter("departureTime"));
            Date arrivalTime = dateFormat.parse(request.getParameter("arrivalTime"));
            int numberOfCarriages = Integer.parseInt(request.getParameter("numberOfCarriages"));
            int trainId = Integer.parseInt(request.getParameter("trainId"));
            String trainType = request.getParameter("trainType");

            if (TrainType.MULTI.toString().equals(trainType) || TrainType.PASSENGER.toString().equals(trainType)) {
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
            if (action.equalsIgnoreCase("tripAdd")) {
                tripService.save(trip);
            } else {
                trip.setId(tripId);
                tripService.update(trip);
            }

        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
    }
}
