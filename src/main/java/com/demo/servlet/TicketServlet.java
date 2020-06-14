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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TicketServlet extends HttpServlet {

    private TripService tripService;

    private TicketService ticketService;

    private HttpSession session;

    private UserService userService;

    private RequestDispatcher requestDispatcher;

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

        if (action.equalsIgnoreCase("ticketDelete")) {
            deleteTicket(request, response);

            return;
        } else if (action.equalsIgnoreCase("ticketUpdate")) {
            showUpdateTicketPage(request);
        } else if (action.equalsIgnoreCase("ticketList")) {
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


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("buyTicket")) {
            createTicket(request, response);
        }
    }

    private void createTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int tripId = Integer.parseInt(request.getParameter("tripId"));
        Trip trip = tripService.getById(tripId);

        int numberOfPlaces = trip.getNumberOfPlaces();
        trip.setNumberOfPlaces(numberOfPlaces - 1);


        Date currentDate = new Date();
        Ticket ticket = new Ticket();
        ticket.setTimeWhenTicketWasBought(currentDate);
        ticket.setTrip(trip);
        session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("loginMessage", "Please login to buy tickets");
            String redirect = request.getContextPath() + "/trip?action=tripDetails&tripId=" + tripId;
            response.sendRedirect(redirect);

            return;
        } else {
            ticket.setUser(user);
            ticketService.save(ticket);

            requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);

        }


    }
}

