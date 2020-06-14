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

    private static final String UPDATE_INFO = "jsp/user/updateUserInfo.jsp";

    private static final String CHANGE_PASSWORD = "jsp/user/changeUserPassword.jsp";

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
        } else if (action.equalsIgnoreCase("userChangePassword")) {
            forward = CHANGE_PASSWORD;
        }


        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("userRegister")) {
            registerUser(request, response);

        } else if (action.equalsIgnoreCase("userLogin")) {
            loginUser(request, response);
        } else if (action.equalsIgnoreCase("userUpdateInfo")) {
            changeUserInfo(request, response);
        } else if (action.equalsIgnoreCase("userChangePassword")) {
            changePassword(request, response);
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        session = request.getSession();
        User user = (User) session.getAttribute("user");

        user.setPassword(request.getParameter("password"));
        String repeatedPassword = request.getParameter("repeatedPassword");

        boolean isPasswordValid = userService.changePassword(user, repeatedPassword);

        if (isPasswordValid) {
            String userPageRedirect = request.getContextPath() + "/user?action=userPage";
            response.sendRedirect(userPageRedirect);

        } else {
            request.setAttribute("passwordValidation", "Password must contain: " +
                    "1. Capital letter;" +
                    "2. 8 letters size;" +
                    "3. Digits");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(CHANGE_PASSWORD);
            requestDispatcher.forward(request, response);
        }
    }

    private void changeUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        session = request.getSession();
        User user = (User) session.getAttribute("user");

        user.setUsername(request.getParameter("username"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));

        String registerValidation = userService.update(user);


        if (!registerValidation.isEmpty()) {
            request.setAttribute("updateValidation", registerValidation);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(UPDATE_INFO);
            requestDispatcher.forward(request, response);
        } else {
            session.setAttribute("user", user);
            String userPageRedirect = request.getContextPath() + "/user?action=userPage";
            response.sendRedirect(userPageRedirect);
        }

    }

    private void updateUser(HttpServletRequest request) {
        forward = UPDATE_INFO;
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

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();

        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setUsername(request.getParameter("username"));

        String registerValidation = userService.save(user);

        if (!registerValidation.isEmpty()) {
            request.setAttribute("registerValidation", registerValidation);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(REGISTER_PAGE);
            requestDispatcher.forward(request, response);
        } else {

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOGIN_PAGE);
            requestDispatcher.forward(request, response);
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.checkLogin(username, password);
        if (user == null) {
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
