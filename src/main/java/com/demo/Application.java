package com.demo;

import com.demo.model.Train;
import com.demo.model.utils.TrainType;
import com.demo.service.TrainService;


public class Application {

    public static void main(String[] args) {

        System.out.println(TrainType.valueOf("CARGO"));
        Train train = new Train();
        train.setTrainType(TrainType.valueOf("CARGO"));
        System.out.println(train.getTrainType());
    }

}
