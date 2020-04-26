package com.demo.service;

import com.demo.dao.impl.TripDaoImpl;
import com.demo.exceptions.TripException;
import com.demo.model.Trip;
import com.demo.model.utils.TrainType;

import java.util.List;

public class TripService {

    TripDaoImpl tripDao = new TripDaoImpl();

    StopService stopService = new StopService();

    public boolean update(Trip trip) {
        if (trip.getTrain().getTrainType() != TrainType.CARGO) {
            return tripDao.update(trip);
        } else {
            throw new TripException("Trip type isn't PASSENGER");
        }
    }

    public boolean delete(Trip trip) {
        return tripDao.delete(trip);
    }

    public boolean deleteById(int id) {
        return tripDao.deleteById(id);
    }

    public List<Trip> getAll() {

        List<Trip> tripList = tripDao.getAll();

        for(Trip trip : tripList) {
            trip.getRoute().setStopsList(stopService.getStopByRouteId(trip.getRoute().getId()));
        }

        return tripList;
    }

    public Trip getById(int id) {
        return tripDao.getById(id);
    }

    public List<Trip> getByRouteId(int routeId) {
        return tripDao.getByRouteId(routeId);
    }

    public Trip save(Trip trip) {
        if (trip.getTrain().getTrainType() != TrainType.CARGO) {
            return tripDao.save(trip);
        } else {
            throw new TripException("Can't save trip because it isn't PASSENGER");
        }
    }
}
