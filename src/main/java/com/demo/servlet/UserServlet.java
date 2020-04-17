package com.demo.servlet;

import com.demo.model.User;
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

public class UserServlet extends HttpServlet {

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger();

    private final String USER_PAGE = "jsp/user/page.jsp";

    private final String LOGIN_PAGE = "jsp/user/login.jsp";

    private final String REGISTER_PAGE = "jsp/user/register.jsp";

    public UserServlet() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("DoGet");
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("page")) {
            forward = USER_PAGE;
            HttpSession httpSession = request.getSession();
            User user = (User) httpSession.getAttribute("user");

            request.setAttribute("user", user);
        } else if (action.equalsIgnoreCase("login")) {
            forward = LOGIN_PAGE;
        } else if (action.equalsIgnoreCase("register")) {
            forward = REGISTER_PAGE;
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

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(USER_PAGE);
            requestDispatcher.forward(request, response);
        }
    }
}
