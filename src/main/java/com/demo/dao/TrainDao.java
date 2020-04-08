package com.demo.dao;

import com.demo.model.Train;

public interface TrainDao extends DAO<Train>{

    Train getByName(String trainName);
}
