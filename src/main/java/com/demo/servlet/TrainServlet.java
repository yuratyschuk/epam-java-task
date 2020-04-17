package com.demo.servlet;

import com.demo.model.Train;
import com.demo.model.utils.TrainType;
import com.demo.service.TrainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TrainServlet extends HttpServlet {

    private final TrainService trainService;

    private final String LIST_TRAIN = "jsp/train/trainList.jsp";

    private final String UPDATE_OR_ADD_TRAIN = "jsp/train/train.jsp";

    private static final Logger logger = LogManager.getLogger();

    public TrainServlet() {
        this.trainService = new TrainService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = " ";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            String redirectString = request.getContextPath() + "/train?action=trainList";

            int trainId = Integer.parseInt(request.getParameter("trainId"));
            trainService.deleteById(trainId);
            request.setAttribute("trainList", trainService.getAll());
            response.sendRedirect(redirectString);
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = UPDATE_OR_ADD_TRAIN;
            int trainId = Integer.parseInt(request.getParameter("trainId"));
            Train train = trainService.getById(trainId);
            request.setAttribute("train", train);
            request.setAttribute("trainTypeList", TrainType.values());


        } else if (action.equalsIgnoreCase("insert")) {
            forward = UPDATE_OR_ADD_TRAIN;
            request.setAttribute("trainType", TrainType.values());
        } else {
            forward = LIST_TRAIN;
            request.setAttribute("trainList", trainService.getAll());
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Train train = new Train();

        train.setTrainName(request.getParameter("trainName"));
        train.setTrainNumber(request.getParameter("trainNumber"));
        train.setMaxNumberOfCarriages(Integer.parseInt(request.getParameter("maxNumberOfCarriages")));
        logger.warn(request.getParameter("trainType"));
        train.setTrainType(TrainType.valueOf(request.getParameter("trainType")));
        logger.info(train.getTrainType());
        String trainId = request.getParameter("trainId");

        if(trainId == null || trainId.isEmpty()) {
            trainService.save(train);
        } else {
            train.setId(Integer.parseInt(trainId));
            trainService.update(train);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_TRAIN);
        request.setAttribute("trainList", trainService.getAll());
        requestDispatcher.forward(request, response);
    }
}
