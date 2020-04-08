package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Train;

public interface TrainDao extends DAO<Train> {

    Train getByName(String trainName);
}
