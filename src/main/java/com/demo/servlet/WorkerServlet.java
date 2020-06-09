package com.demo.servlet;

import com.demo.dao.impl.PositionDaoImpl;
import com.demo.dao.impl.WorkerDaoImpl;
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

    private WorkerService workerService;

    private PositionService positionService;

    private static final String LIST_WORKERS = "jsp/worker/listWorkers.jsp";

    private static final String INSERT_UPDATE_WORKER = "jsp/worker/updateWorker.jsp";

    private static final Logger logger = LogManager.getLogger();


    String forward;

    @Override
    public void init() throws ServletException {

        workerService = new WorkerService(new WorkerDaoImpl());
        positionService = new PositionService(new PositionDaoImpl());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("workerDelete")) {
            deleteWorker(request, response);
            return;
        } else if (action.equalsIgnoreCase("workerUpdate")) {
            updateWorker(request);
        } else if (action.equalsIgnoreCase("workerAdd")) {
            addWorker(request);
        } else if (action.equalsIgnoreCase("workerList")) {
            showListOfWorkers(request);
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        createOrEditWorker(request);

        RequestDispatcher view = request.getRequestDispatcher(LIST_WORKERS);
        request.setAttribute("workerList", workerService.getAll());
        view.forward(request, response);
    }

    private void showListOfWorkers(HttpServletRequest request) {
        forward = LIST_WORKERS;

        request.setAttribute("workerList", workerService.getAll());

    }

    private void addWorker(HttpServletRequest request) {
        request.setAttribute("positionList", positionService.getAll());
        forward = INSERT_UPDATE_WORKER;
    }

    private void updateWorker(HttpServletRequest request) {
        forward = INSERT_UPDATE_WORKER;

        int workerId = Integer.parseInt(request.getParameter("workerId"));
        Worker worker = workerService.getById(workerId);

        request.setAttribute("worker", worker);
        request.setAttribute("positionList", positionService.getAll());
    }

    private void deleteWorker(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String workerListRedirect = request.getContextPath() + "/worker?action=workerList";

        int workerId = Integer.parseInt(request.getParameter("workerId"));
        workerService.deleteById(workerId);

        request.setAttribute("workerList", workerService.getAll());
        response.sendRedirect(workerListRedirect);
    }


    private void createOrEditWorker(HttpServletRequest request) {
        String action = request.getParameter("action");

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

        if (action.equalsIgnoreCase("workerAdd")) {
            workerService.save(worker);

        } else if (action.equalsIgnoreCase("workerUpdate")) {
            int workerId = Integer.parseInt(request.getParameter("workerId"));

            worker.setId(workerId);
            workerService.update(worker);
        }

    }
}
