package com.demo.service;

import com.demo.dao.impl.TripDaoImpl;
import com.demo.model.Trip;

import java.util.List;

public class TripService {

    TripDaoImpl tripDao = new TripDaoImpl();

    public boolean update(Trip trip) {
        return tripDao.update(trip);
    }

    public boolean delete(Trip trip) {
        return tripDao.delete(trip);
    }

    public boolean deleteById(int id) {
        return tripDao.deleteById(id);
    }

    public List<Trip> getAll() {
        return tripDao.getAll();
    }

    public Trip getById(int id) {
        return tripDao.getById(id);
    }

    public boolean save(Trip trip) {
        return tripDao.save(trip);
    }
}
