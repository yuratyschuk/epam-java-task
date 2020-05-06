package com.demo.servlet;

import com.demo.model.Ticket;
import com.demo.model.User;
import com.demo.service.TicketService;
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
import java.util.List;

public class UserServlet extends HttpServlet {

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger();

    private final String USER_PAGE = "jsp/user/page.jsp";

    private final String LOGIN_PAGE = "jsp/user/login.jsp";

    private final String REGISTER_PAGE = "jsp/user/register.jsp";

    private HttpSession session;

    private String forward;

    private final TicketService ticketService;

    public UserServlet() {
        userService = new UserService();
        ticketService = new TicketService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("page")) {
            forward = USER_PAGE;
            session = request.getSession();
            User user = (User) session.getAttribute("user");

            List<Ticket> ticketList = ticketService.getTicketListByUserId(user.getId());

            request.setAttribute("ticketList", ticketList);
            request.setAttribute("user", user);
        } else if (action.equalsIgnoreCase("login")) {
            forward = LOGIN_PAGE;
        } else if (action.equalsIgnoreCase("register")) {
            forward = REGISTER_PAGE;
        } else if(action.equalsIgnoreCase("userDelete")) {
            forward = "index.jsp";
            session = request.getSession();
            User user = (User) session.getAttribute("user");

            userService.delete(user);

        } else if(action.equalsIgnoreCase("userUpdate")) {
            forward = REGISTER_PAGE;
            session = request.getSession();
            User user = (User) session.getAttribute("user");

            request.setAttribute("user", user);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        logger.info(action);
        if (action.equalsIgnoreCase("register")) {
            logger.info("Here register");

            User user = new User();

            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setUsername(request.getParameter("username"));

            userService.save(user);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOGIN_PAGE);
            requestDispatcher.forward(request, response);
        } else if (action.equalsIgnoreCase("login")) {
            logger.info("Here login");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            User user = userService.checkLogin(username, password);

            session = request.getSession();
            session.setAttribute("user", user);
            String userPageRedirect = request.getContextPath() + "/user?action=page";
            response.sendRedirect(userPageRedirect);

        }
    }
}
