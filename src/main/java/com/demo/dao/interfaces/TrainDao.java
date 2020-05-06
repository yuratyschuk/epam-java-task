package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Train;
import com.demo.model.utils.TrainType;

import java.util.List;

public interface TrainDao extends DAO<Train> {

    Train getByName(String trainName);

    List<Train> getTrainListByType(TrainType trainType);
}
