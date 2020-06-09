package com.demo.service;

import com.demo.dao.interfaces.StopDao;
import com.demo.dao.interfaces.TripDao;
import com.demo.model.Stop;
import com.demo.model.Trip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripService {

    private final TripDao tripDao;
    
    private final StopDao stopDao;
    
    public TripService(TripDao tripDao, StopDao stopDao) {
        this.tripDao = tripDao;
        this.stopDao = stopDao;
    }
    
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
        List<Trip> tripList = tripDao.getAll();

        Set<Stop> stopSet = new HashSet<>();
        for (Trip trip : tripList) {
            stopSet.addAll(stopDao.getStopByRouteId(trip.getId()));
            trip.setStopSet(stopSet);
        }

        return tripList;
    }

    public Trip getById(int id) {
        Trip trip = tripDao.getById(id);
        Set<Stop> stops = stopDao.getStopByRouteId(trip.getId());

        trip.setStopSet(stops);
        return trip;

    }

    public List<Trip> getByRouteId(int routeId) {
        return tripDao.getByRouteId(routeId);
    }

    public Trip save(Trip trip) {
        return tripDao.save(trip);

    }

    public boolean saveToStopTripTable(int tripId, int stopId) {
        return tripDao.saveToStopTripTable(tripId, stopId);
    }
}
