package com.demo.servlet;

import com.demo.dao.impl.StopDaoImpl;
import com.demo.dao.impl.TicketDaoImpl;
import com.demo.dao.impl.TripDaoImpl;
import com.demo.dao.impl.UserDaoImpl;
import com.demo.model.Ticket;
import com.demo.model.Trip;
import com.demo.model.User;
import com.demo.service.TicketService;
import com.demo.service.TripService;
import com.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TicketServlet extends HttpServlet {

    private  TripService tripService;

    private  TicketService ticketService;

    private UserService userService;

    private final static Logger logger = LogManager.getLogger();

    private static final String BUY_TICKET = "jsp/ticket/buyTicket.jsp";

    private static final String TRIP_LIST = "jsp/ticket/ticketList.jsp";

    private String forward;


    @Override
    public void init() throws ServletException {
        tripService = new TripService(new TripDaoImpl(), new StopDaoImpl());
        ticketService = new TicketService(new TicketDaoImpl());
        userService = new UserService(new UserDaoImpl());

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("buyTicket")) {
            showTicketBuyPage(request);
        } else if(action.equalsIgnoreCase("ticketDelete")) {
            deleteTicket(request, response);

            return;
        } else if(action.equalsIgnoreCase("ticketUpdate")) {
            showUpdateTicketPage(request);
        } else if(action.equalsIgnoreCase("ticketList")) {
            showTicketListPage(request);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);

    }

    private void showTicketListPage(HttpServletRequest request) {
        forward = TRIP_LIST;
        request.setAttribute("ticketList", ticketService.getAll());
    }

    private void showUpdateTicketPage(HttpServletRequest request) {
        forward = BUY_TICKET;
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));

        request.setAttribute("ticket", ticketService.getById(ticketId));
    }

    private void deleteTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirect = request.getContextPath() + "/ticket?action=ticketList";

        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        ticketService.deleteById(ticketId);

        response.sendRedirect(redirect);
    }

    private void showTicketBuyPage(HttpServletRequest request) {
        forward = BUY_TICKET;
        int tripId = Integer.parseInt(request.getParameter("tripId"));
        Trip trip = tripService.getById(tripId);

        request.setAttribute("trip", trip);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        createTicket(request);

    }

    private void createTicket(HttpServletRequest request) {
        int tripId = Integer.parseInt(request.getParameter("tripId"));
        Trip trip = tripService.getById(tripId);

        int numberOfPlaces = trip.getNumberOfPlaces();
        trip.setNumberOfPlaces(numberOfPlaces - 1);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword("password");
        user.setUsername("username");


        Date currentDate = new Date();
        Ticket ticket = new Ticket();
        ticket.setTimeWhenTicketWasBought(currentDate);
        ticket.setTrip(trip);

        user = userService.save(user);
        ticket.setUser(user);

        ticketService.save(ticket);
    }
}

