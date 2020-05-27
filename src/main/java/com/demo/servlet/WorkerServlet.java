package com.demo.servlet;

import com.demo.model.Position;
import com.demo.model.Worker;
import com.demo.service.PositionService;
import com.demo.service.WorkerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class WorkerServlet extends HttpServlet {

    private final WorkerService workerService;
    private final String LIST_WORKERS = "jsp/worker/listWorkers.jsp";
    private final String INSERT_UPDATE_WORKER = "jsp/worker/updateWorker.jsp";
    private String forward;

    private static final Logger logger = LogManager.getLogger();
    private final PositionService positionService;

    public WorkerServlet() {
        positionService = new PositionService();
        workerService = new WorkerService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            String workerListRedirect = request.getContextPath() + "/worker?action=listWorkers";

            int workerId = Integer.parseInt(request.getParameter("workerId"));
            workerService.deleteById(workerId);
            request.setAttribute("workerList", workerService.getAll());
            response.sendRedirect(workerListRedirect);
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_UPDATE_WORKER;

            int workerId = Integer.parseInt(request.getParameter("workerId"));
            Worker worker = workerService.getById(workerId);
            request.setAttribute("worker", worker);

            List<Position> positionList = positionService.getAll();
            request.setAttribute("positionList", positionList);

        } else if (action.equalsIgnoreCase("insert")) {
            List<Position> positionList = positionService.getAll();
            request.setAttribute("positionList", positionList);
            forward = INSERT_UPDATE_WORKER;
        } else {
            forward = LIST_WORKERS;
            request.setAttribute("workerList", workerService.getAll());
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int workingExperience = Integer.parseInt(request.getParameter("workingExperience"));
        Date hireDate = Date.valueOf(request.getParameter("hireDate"));
        int positionId = Integer.parseInt(request.getParameter("position"));

        Worker worker = new Worker();
        worker.setFirstName(firstName);
        worker.setLastName(lastName);
        worker.setWorkingExperience(workingExperience);
        worker.setHireDate(hireDate);

        Position position = new Position();
        position.setId(positionId);
        worker.setPosition(position);

        String workerId = request.getParameter("workerId");
        if (workerId == null || workerId.isEmpty()) {
            workerService.save(worker);
        } else {
            worker.setId(Integer.parseInt(workerId));
            workerService.update(worker);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_WORKERS);
        request.setAttribute("workerList", workerService.getAll());
        view.forward(request, response);
    }
}
