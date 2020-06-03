package com.demo.service;

import com.demo.dao.interfaces.TrainDao;
import com.demo.model.Train;
import com.demo.model.utils.TrainType;

import java.util.List;

public class TrainService {

    private final TrainDao trainDao;

    public TrainService(TrainDao trainDao) {
        this.trainDao = trainDao;
    }

    public boolean update(Train train) {
        return trainDao.update(train);
    }

    public List<Train> getAll() {
        return trainDao.getAll();
    }

    public Train getByTrainName(String trainName) {
        return trainDao.getByName(trainName);
    }

    public Train getById(int id) {
        return trainDao.getById(id);
    }

    public boolean delete(Train train) {
        return trainDao.delete(train);
    }

    public boolean deleteById(int id) {
        return trainDao.deleteById(id);
    }

    public Train save(Train train) {
        return trainDao.save(train);
    }

    public List<Train> getTrainListByType(TrainType trainType) {
        return trainDao.getTrainListByType(trainType);
    }
}
