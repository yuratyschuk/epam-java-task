package com.demo.service;

import com.demo.dao.impl.RouteDaoImpl;
import com.demo.model.Route;

import java.util.List;

public class RouteService {

    RouteDaoImpl routeDao = new RouteDaoImpl();

    public Route save(Route route) {
            routeDao.save(route);
            return route;
    }

    public boolean update(Route route) {
        if (!route.getDeparturePlace().getPlaceName().equals(route.getArrivalPlace().getPlaceName())) {
            routeDao.update(route);
            return true;
        }
        return false;
    }

    public Route getByDeparturePlaceIdAndArrivalPlaceId(Integer departurePlaceId, Integer arrivalPlaceId) {
        return routeDao.getByArrivalPlaceIdAndDeparturePlaceId(departurePlaceId, arrivalPlaceId);
    }

    public Route getByDeparturePlaceNameAndArrivalPlaceName(String departureName, String arrivalName) {
        return routeDao.getByArrivalPlaceNameAndDeparturePlaceName(departureName, arrivalName);
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
