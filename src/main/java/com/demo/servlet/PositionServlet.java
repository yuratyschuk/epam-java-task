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
        positionList = positionService.getAll();

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        action = request.getParameter("action");

        if (action.equalsIgnoreCase("positionList")) {
            forward = LIST_POSITIONS;

            request.setAttribute("positionList", positionList);
        } else if (action.equalsIgnoreCase("positionListActive")) {
            forward = LIST_POSITIONS;
            List<Position> positionListActive = positionService.getPositionListByActive(positionList, true);
            request.setAttribute("positionList", positionListActive);


        } else if (action.equalsIgnoreCase("positionAdd")) {
            forward = ADD_POSITION;

        } else if (action.equalsIgnoreCase("positionDelete")) {
            String positionRedirect = request.getContextPath() + "/position?action=positionList";
            int positionId = Integer.parseInt(request.getParameter("positionId"));

            positionService.deleteById(positionId);
            response.sendRedirect(positionRedirect);
            return;
        } else if (action.equalsIgnoreCase("positionUpdate")) {
            forward = ADD_POSITION;
            int positionId = Integer.parseInt(request.getParameter("positionId"));

            request.setAttribute("position", positionService.getById(positionId));

        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
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
