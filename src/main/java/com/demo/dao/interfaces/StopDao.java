package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Stop;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StopDao extends DAO<Stop> {
    Stop getByName(String name);

    Set<Stop> getStopByRouteId(int routeId);
}
