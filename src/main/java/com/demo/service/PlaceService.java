package com.demo.service;

import com.demo.dao.interfaces.PlacesDao;
import com.demo.model.Places;

import java.util.List;

public class PlaceService {

    private final PlacesDao placesDao;

    public PlaceService(PlacesDao placesDao) {
        this.placesDao = placesDao;
    }

    public List<Places> getAll() {
        return placesDao.getAll();
    }
}
