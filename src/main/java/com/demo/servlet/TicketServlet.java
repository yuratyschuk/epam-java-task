package com.demo.servlet;

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
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ServletSecurity
public class TicketServlet extends HttpServlet {

    private final TripService tripService;

    private final TicketService ticketService;

    private final UserService userService;

    private final static Logger logger = LogManager.getLogger();

    private final String BUY_TICKET = "jsp/ticket/buyTicket.jsp";

    private final String TRIP_LIST = "jsp/ticket/ticketList.jsp";

    private String forward;

    private String action;

    public TicketServlet() {
        tripService = new TripService();
        ticketService = new TicketService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        action = request.getParameter("action");

        if (action.equalsIgnoreCase("buyTicket")) {

            forward = BUY_TICKET;
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            Trip trip = tripService.getById(tripId);

            request.setAttribute("trip", trip);
        } else if(action.equalsIgnoreCase("ticketDelete")) {
            String redirect = request.getContextPath() + "/trip?action=ticketList";

            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            ticketService.deleteById(ticketId);

            response.sendRedirect(redirect);
            return;

        } else if(action.equalsIgnoreCase("ticketUpdate")) {
            forward = BUY_TICKET;
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));

            request.setAttribute("ticket", ticketService.getById(ticketId));
        } else if(action.equalsIgnoreCase("ticketList")) {
            forward = TRIP_LIST;
            request.setAttribute("ticketList", ticketService.getAll());
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
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

