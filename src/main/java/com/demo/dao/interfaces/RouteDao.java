package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Route;

import java.util.Optional;

public interface RouteDao extends DAO<Route> {

    Route getByArrivalPlaceIdAndDeparturePlaceId(Integer departurePlaceId, Integer arrivalPlaceId);

}
