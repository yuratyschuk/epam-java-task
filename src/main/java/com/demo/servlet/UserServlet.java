package com.demo.servlet;

import com.demo.dao.impl.TicketDaoImpl;
import com.demo.dao.impl.UserDaoImpl;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {

    private UserService userService;

    private static final Logger logger = LogManager.getLogger();

    private static final String USER_PAGE = "jsp/user/page.jsp";

    private static final String LOGIN_PAGE = "jsp/user/login.jsp";

    private static final String REGISTER_PAGE = "jsp/user/register.jsp";

    private HttpSession session;

    private String forward;

    private TicketService ticketService;


    @Override
    public void init() throws ServletException {
        userService = new UserService(new UserDaoImpl());
        ticketService = new TicketService(new TicketDaoImpl());

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("userPage")) {
            showUserPage(request);
        } else if (action.equalsIgnoreCase("userLogin")) {
            forward = LOGIN_PAGE;
        } else if (action.equalsIgnoreCase("userRegister")) {
            forward = REGISTER_PAGE;
        } else if (action.equalsIgnoreCase("userDelete")) {
            deleteUser(request);
        } else if (action.equalsIgnoreCase("userUpdate")) {
            updateUser(request);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("userRegister")) {
            registerUser(request);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOGIN_PAGE);
            requestDispatcher.forward(request, response);
        } else if (action.equalsIgnoreCase("userLogin")) {
            loginUser(request, response);
        }
    }

    private void updateUser(HttpServletRequest request) {
        forward = REGISTER_PAGE;
        session = request.getSession();
        User user = (User) session.getAttribute("user");

        request.setAttribute("user", user);
    }

    private void deleteUser(HttpServletRequest request) {
        forward = "index.jsp";
        session = request.getSession();
        User user = (User) session.getAttribute("user");

        userService.delete(user);
    }

    private void showUserPage(HttpServletRequest request) {
        forward = USER_PAGE;
        session = request.getSession();
        User user = (User) session.getAttribute("user");

        List<Ticket> ticketList = ticketService.getTicketListByUserId(user.getId());

        request.setAttribute("ticketList", ticketList);
        request.setAttribute("user", user);
    }

    private void registerUser(HttpServletRequest request) {
        User user = new User();

        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setUsername(request.getParameter("username"));

        userService.save(user);

    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.checkLogin(username, password);
        if(user == null) {
            request.setAttribute("errorMessage", "Login or password are incorrect");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOGIN_PAGE);
            requestDispatcher.forward(request, response);
        } else {
            session = request.getSession();
            session.setAttribute("user", user);
            String userPageRedirect = request.getContextPath() + "/user?action=userPage";
            response.sendRedirect(userPageRedirect);

        }


    }
}
