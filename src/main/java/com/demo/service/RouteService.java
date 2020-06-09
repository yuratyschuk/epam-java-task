package com.demo.service;

import com.demo.dao.interfaces.RouteDao;
import com.demo.model.Route;

import java.util.List;

public class RouteService {

    private final RouteDao routeDao;

    public RouteService(RouteDao routeDao) {
        this.routeDao = routeDao;
    }

    public Route save(Route route) {

        return routeDao.save(route);
    }

    public boolean update(Route route) {
        if (!route.getDeparturePlace().getPlaceName().equals(route.getArrivalPlace().getPlaceName())) {
            return  routeDao.update(route);

        }
        return false;
    }

    public Route getByDeparturePlaceIdAndArrivalPlaceId(Integer departurePlaceId, Integer arrivalPlaceId) {
        return routeDao.getByArrivalPlaceIdAndDeparturePlaceId(departurePlaceId, arrivalPlaceId);
    }

    public Route getById(Integer id) {
        return routeDao.getById(id);
    }

    public List<Route> getAll() {
        return routeDao.getAll();
    }

    public boolean deleteById(Integer id) {
        return routeDao.deleteById(id);
    }

    public boolean delete(Route route) {
        return routeDao.delete(route);
    }


}
