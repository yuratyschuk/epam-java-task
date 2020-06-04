package com.demo.service;

import com.demo.dao.impl.PlacesDaoImpl;
import com.demo.dao.interfaces.PlacesDao;
import com.demo.model.Places;

import java.util.List;


public class PlaceService {


    private static volatile PlaceService instance;

    public static PlaceService getInstance() {
        PlaceService localInstance = instance;
        if (localInstance == null) {
            synchronized (PlaceService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PlaceService(new PlacesDaoImpl());
                }
            }
        }
        return localInstance;
    }

    private final PlacesDao placesDao;

    private PlaceService(PlacesDao placesDao) {
        this.placesDao = placesDao;
    }

    public List<Places> getAll() {
        return placesDao.getAll();
    }
}
