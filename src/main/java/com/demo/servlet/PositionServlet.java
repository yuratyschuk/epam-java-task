package com.demo.servlet;

import com.demo.dao.impl.PositionDaoImpl;
import com.demo.model.Position;
import com.demo.service.PositionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class PositionServlet extends HttpServlet {

    private String action;

    private PositionService positionService;

    private static final String LIST_POSITIONS = "jsp/position/positionList.jsp";

    private static final String ADD_POSITION = "jsp/position/positionDetails.jsp";

    private static final Logger logger = LogManager.getLogger();

    private String forward;

    private List<Position> positionList;

    public PositionServlet() {
    }

    @Override
    public void init() throws ServletException {
        positionService = new PositionService(new PositionDaoImpl());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        action = request.getParameter("action");

        if (action.equalsIgnoreCase("positionList")) {
            showPositionList(request);
        } else if (action.equalsIgnoreCase("positionListActive")) {
            showPositionListActive(request);
        } else if (action.equalsIgnoreCase("positionAdd")) {
            forward = ADD_POSITION;
        } else if (action.equalsIgnoreCase("positionDelete")) {
            deletePosition(request, response);

            return;
        } else if (action.equalsIgnoreCase("positionUpdate")) {
            showUpdatePage(request);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    private void showUpdatePage(HttpServletRequest request) {
        forward = ADD_POSITION;
        int positionId = Integer.parseInt(request.getParameter("positionId"));

        request.setAttribute("position", positionService.getById(positionId));
    }

    private void deletePosition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String positionRedirect = request.getContextPath() + "/position?action=positionList";
        int positionId = Integer.parseInt(request.getParameter("positionId"));

        positionService.deleteById(positionId);

        response.sendRedirect(positionRedirect);
    }

    private void showPositionListActive(HttpServletRequest request) {
        forward = LIST_POSITIONS;
        List<Position> positionListActive = positionService.getPositionListByActive(positionList, true);
        request.setAttribute("positionList", positionListActive);
    }

    private void showPositionList(HttpServletRequest request) {
        forward = LIST_POSITIONS;

        request.setAttribute("positionList", positionService.getAll());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        createOrUpdatePosition(request);

        String positionRedirect = request.getContextPath() + "/position?action=positionList";
        response.sendRedirect(positionRedirect);
    }

    private void createOrUpdatePosition(HttpServletRequest request) {
        action = request.getParameter("action");

        String jobName = request.getParameter("jobName");
        BigDecimal salary = BigDecimal.valueOf(Long.parseLong(request.getParameter("salary")));
        boolean active = Boolean.parseBoolean(request.getParameter("active"));

        Position position = new Position();
        position.setJobName(jobName);
        position.setSalary(salary);
        position.setActive(active);

        if (action.equalsIgnoreCase("positionAdd")) {
            positionService.save(position);

        } else if (action.equalsIgnoreCase("positionUpdate")) {
            int positionId = Integer.parseInt(request.getParameter("positionId"));

            position.setId(positionId);
            positionService.update(position);

        }
    }
}
