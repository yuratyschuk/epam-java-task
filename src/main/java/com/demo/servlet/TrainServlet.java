package com.demo.servlet;

import com.demo.dao.impl.TrainDaoImpl;
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
import java.util.List;

public class TrainServlet extends HttpServlet {

    private TrainService trainService;

    private final String LIST_TRAIN = "jsp/train/trainList.jsp";

    private static final String UPDATE_OR_ADD_TRAIN = "jsp/train/train.jsp";

    private static final Logger logger = LogManager.getLogger();


    private String forward;

    @Override
    public void init() throws ServletException {
        trainService = new TrainService(new TrainDaoImpl());

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("trainDelete")) {
            deleteTrain(request, response);
            return;
        } else if (action.equalsIgnoreCase("trainUpdate")) {
            forward = showUpdateTrainPage(request);
        } else if (action.equalsIgnoreCase("trainAdd")) {
            forward = showTrainAddPage(request);
        } else if(action.equalsIgnoreCase("trainList")) {
            forward = showTrainListPage(request);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    private String showTrainListPage(HttpServletRequest request) {
        forward = LIST_TRAIN;
        request.setAttribute("trainList", trainService.getAll());

        return forward;
    }

    private String showTrainAddPage(HttpServletRequest request) {
        forward = UPDATE_OR_ADD_TRAIN;
        request.setAttribute("trainTypeEnum", TrainType.values());
        return forward;
    }

    private String showUpdateTrainPage(HttpServletRequest request) {
        forward = UPDATE_OR_ADD_TRAIN;
        int trainId = Integer.parseInt(request.getParameter("trainId"));
        Train train = trainService.getById(trainId);
        request.setAttribute("train", train);
        request.setAttribute("trainTypeEnum", TrainType.values());
        return forward;
    }

    private void deleteTrain(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectString = "/train?action=trainList";

        int trainId = Integer.parseInt(request.getParameter("trainId"));
        trainService.deleteById(trainId);

        request.setAttribute("trainList", trainService.getAll());
        response.sendRedirect(redirectString);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createOrEditTrain(request);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_TRAIN);
        request.setAttribute("trainList", trainService.getAll());
        requestDispatcher.forward(request, response);
    }

    private void createOrEditTrain(HttpServletRequest request) {
        String action = request.getParameter("action");


        String trainName = request.getParameter("trainName");
        String trainNumber = request.getParameter("trainNumber");
        int maxNumberOfCarriages = Integer.parseInt(request.getParameter("maxNumberOfCarriages"));
        TrainType trainType = TrainType.valueOf(request.getParameter("trainType"));

        Train train = new Train();
        train.setTrainName(trainName);
        train.setTrainNumber(trainNumber);
        train.setMaxNumberOfCarriages(maxNumberOfCarriages);
        train.setTrainType(trainType);

        if (action.equalsIgnoreCase("trainAdd")) {
            trainService.save(train);

        } else if (action.equalsIgnoreCase("trainUpdate")) {
            int trainId = Integer.parseInt(request.getParameter("trainId"));

            train.setId(trainId);
            trainService.update(train);
        }

    }
}

