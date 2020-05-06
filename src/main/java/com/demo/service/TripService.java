package com.demo.service;

import com.demo.dao.impl.TripDaoImpl;
import com.demo.exceptions.TripException;
import com.demo.model.Stop;
import com.demo.model.Trip;
import com.demo.model.utils.TrainType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<Stop> stopSet = new HashSet<>();

        for (Trip trip : tripList) {
            stopSet.addAll(stopService.getStopByRouteId(trip.getRoute().getId()));
            trip.setStopSet(stopSet);
        }

        return tripList;
    }

    public Trip getById(int id) {
        Trip trip = tripDao.getById(id);
        Set<Stop> stops = stopService.getStopByRouteId(trip.getRoute().getId());

        trip.setStopSet(stops);
        return trip;

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
