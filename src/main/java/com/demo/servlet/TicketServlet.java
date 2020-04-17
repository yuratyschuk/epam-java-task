package com.demo.servlet;

import com.demo.model.User;
import com.demo.model.Ticket;
import com.demo.model.Trip;
import com.demo.service.UserService;
import com.demo.service.TicketService;
import com.demo.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

@ServletSecurity
public class TicketServlet extends HttpServlet {

    private final TripService tripService;

    private final TicketService ticketService;

    private final UserService userService;

    private final static Logger logger = LogManager.getLogger();

    public TicketServlet() {
        tripService = new TripService();
        ticketService = new TicketService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            String forward = " ";
            String action = request.getParameter("action");

            if (action.equalsIgnoreCase("buyTicket")) {
                forward = "jsp/ticket/buyTicket.jsp";

                Trip trip = tripService.getById(Integer.parseInt(request.getParameter("tripId")));
                logger.info("Ticket: " + trip);

                request.setAttribute("trip", trip);
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
            requestDispatcher.forward(request, response);

        } catch (NumberFormatException | ServletException | IOException e) {
            logger.error(e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            Ticket ticket = new Ticket();

            Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
            ticket.setTimeWhenTicketWasBought(currentDate);
            logger.info(request.getParameter("tripId"));
            ticket.setTrip(tripService.getById(Integer.parseInt(request.getParameter("tripId"))));

            String email = request.getParameter("email");
            User client = userService.getByEmail(email);
            if (client == null) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");


                User clientToSave = new User();
                clientToSave.setFirstName(firstName);
                clientToSave.setLastName(lastName);
                clientToSave.setEmail(email);

                client = userService.save(clientToSave);
                clientToSave.setId(client.getId());
                ticket.setClient(clientToSave);
            ticketService.save(ticket);
            } else {
                ticket.setClient(client);
                ticketService.save(ticket);
            }
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
        }
    }
}
